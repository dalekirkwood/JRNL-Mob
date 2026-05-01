package com.dale.jrnlmob.ui.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dale.jrnlmob.data.location.LocationHelper
import com.dale.jrnlmob.data.location.LocationResult
import com.dale.jrnlmob.data.parser.JrnlFileParser
import com.dale.jrnlmob.data.preferences.AppPreferences
import com.dale.jrnlmob.data.preferences.SyncSettings
import com.dale.jrnlmob.data.sync.WebDavClient
import com.dale.jrnlmob.data.weather.WeatherService
import com.dale.jrnlmob.domain.model.JournalEntry
import com.dale.jrnlmob.domain.model.Mood
import com.dale.jrnlmob.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class ComposeState(
    val body: String = "",
    val selectedMood: Mood? = null,
    val location: String = "",
    val weather: String = "",
    val isLoadingLocation: Boolean = false,
    val isLoadingWeather: Boolean = false,
    val locationError: String? = null,
    val isEditing: Boolean = false,
    val editEntryId: Long? = null,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: JournalRepository,
    private val locationHelper: LocationHelper,
    private val weatherService: WeatherService,
    private val webDavClient: WebDavClient,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(ComposeState())
    val state: StateFlow<ComposeState> = _state.asStateFlow()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private var syncSettings = SyncSettings()

    init {
        viewModelScope.launch {
            preferences.syncSettings.collect { settings ->
                syncSettings = settings
            }
        }
    }

    fun loadEntry(entryId: Long) {
        viewModelScope.launch {
            repository.getEntryById(entryId).collect { entry ->
                if (entry != null) {
                    _state.update {
                        it.copy(
                            body = entry.body,
                            selectedMood = entry.mood,
                            location = entry.location ?: "",
                            weather = entry.weather ?: "",
                            isEditing = true,
                            editEntryId = entry.id
                        )
                    }
                }
            }
        }
    }

    fun onBodyChanged(text: String) {
        _state.update { it.copy(body = text) }
    }

    fun onMoodSelected(mood: Mood) {
        _state.update {
            it.copy(selectedMood = if (it.selectedMood == mood) null else mood)
        }
    }

    fun fetchLocation() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingLocation = true, locationError = null) }
            when (val result = locationHelper.getCurrentLocation()) {
                is LocationResult.Success -> {
                    _state.update {
                        it.copy(
                            location = result.address,
                            isLoadingLocation = false
                        )
                    }
                    fetchWeather(result.latitude, result.longitude)
                }
                is LocationResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingLocation = false,
                            locationError = result.message
                        )
                    }
                }
            }
        }
    }

    private suspend fun fetchWeather(latitude: Double, longitude: Double) {
        _state.update { it.copy(isLoadingWeather = true) }
        val weather = weatherService.getWeather(latitude, longitude, syncSettings.temperatureUnit)
        _state.update {
            it.copy(
                weather = weather?.let { "${it.icon} ${it.temperature}, ${it.condition}" } ?: "",
                isLoadingWeather = false
            )
        }
    }

    fun save() {
        val currentBody = _state.value.body.trim()
        if (currentBody.isBlank()) return

        viewModelScope.launch {
            try {
                val now = LocalDateTime.now().format(dateTimeFormatter)
                val title = currentBody.lines().first().trim().take(100)
                val tags = Regex("@\\w+").findAll(currentBody).map { it.value.removePrefix("@") }.toList()

                var dateTime = now
                if (_state.value.isEditing && _state.value.editEntryId != null) {
                    repository.getEntryById(_state.value.editEntryId!!).collect { existing ->
                        if (existing != null) {
                            dateTime = existing.dateTime
                        }
                        saveEntry(dateTime, title, currentBody, tags)
                    }
                } else {
                    saveEntry(now, title, currentBody, tags)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private suspend fun saveEntry(dateTime: String, title: String, body: String, tags: List<String>) {
        val entry = JournalEntry(
            id = _state.value.editEntryId ?: 0,
            dateTime = dateTime,
            title = title,
            body = body,
            mood = _state.value.selectedMood,
            location = _state.value.location.ifBlank { null },
            weather = _state.value.weather.ifBlank { null },
            tags = tags,
            updatedAt = System.currentTimeMillis()
        )
        repository.saveEntry(entry)
        try {
            uploadJournal()
            _state.update { it.copy(isSaved = true, error = null) }
        } catch (e: Exception) {
            _state.update { it.copy(isSaved = true, error = "Saved locally; upload failed: ${e.message}") }
        }
    }

    private suspend fun uploadJournal() {
        val settings = preferences.syncSettings.first()
        if (settings.webdavUrl.isBlank()) return
        val allEntries = repository.getAllEntries().first()
        if (allEntries.isEmpty()) return

        val entities = allEntries.map { e ->
            com.dale.jrnlmob.data.local.entity.EntryEntity(
                id = e.id, dateTime = e.dateTime, title = e.title, body = e.body,
                mood = e.mood?.name, location = e.location, weather = e.weather,
                tags = if (e.tags.isNotEmpty()) e.tags.joinToString(",") else null,
                createdAt = e.createdAt, updatedAt = e.updatedAt
            )
        }
        val content = JrnlFileParser.format(entities)
        webDavClient.uploadFile(
            settings.webdavUrl, settings.username,
            settings.password, settings.journalFilePath, content
        )
    }

    fun reset() {
        _state.update { ComposeState() }
    }
}
