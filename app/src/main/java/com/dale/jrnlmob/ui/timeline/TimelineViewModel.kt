package com.dale.jrnlmob.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dale.jrnlmob.data.parser.JrnlFileParser
import com.dale.jrnlmob.data.preferences.AppPreferences
import com.dale.jrnlmob.data.sync.WebDavClient
import com.dale.jrnlmob.domain.model.JournalEntry
import com.dale.jrnlmob.domain.model.Mood
import com.dale.jrnlmob.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TimelineState(
    val entries: List<JournalEntry> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isSyncing: Boolean = false,
    val syncMessage: String? = null,
    val scrollToTop: Boolean = false
)

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: JournalRepository,
    private val webDavClient: WebDavClient,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(TimelineState())
    val state: StateFlow<TimelineState> = _state.asStateFlow()

    private var syncSettings = com.dale.jrnlmob.data.preferences.SyncSettings()

    init {
        loadEntries()
        viewModelScope.launch {
            preferences.syncSettings.collect { settings ->
                syncSettings = settings
            }
        }
    }

    private fun loadEntries() {
        viewModelScope.launch {
            repository.getAllEntries().collect { entries ->
                _state.update {
                    it.copy(
                        entries = entries,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                loadEntries()
            } else {
                repository.searchEntries(query).collect { entries ->
                    _state.update { it.copy(entries = entries) }
                }
            }
        }
    }

    fun syncFromWebdav() {
        val settings = syncSettings
        if (settings.webdavUrl.isBlank()) {
            _state.update { it.copy(syncMessage = "Configure sync settings first") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSyncing = true, syncMessage = "Downloading journal...") }
            val result = webDavClient.downloadFile(
                baseUrl = settings.webdavUrl,
                username = settings.username,
                password = settings.password,
                filePath = settings.journalFilePath
            )

            result.fold(
                onSuccess = { content ->
                    if (content.isBlank()) {
                        _state.update { it.copy(isSyncing = false, syncMessage = "Journal file is empty") }
                        return@launch
                    }
                    _state.update { it.copy(syncMessage = "Importing entries...") }
                    repository.deleteAllEntries()
                    val entities = JrnlFileParser.parse(content)
                    var count = 0
                    entities.forEach { entity ->
                        android.util.Log.d("TimelineVM", "sync: saving entry title=${entity.title} mood=${entity.mood} location=${entity.location} weather=${entity.weather} tags=${entity.tags}")
                        repository.saveEntry(
                            JournalEntry(
                                dateTime = entity.dateTime,
                                title = entity.title,
                                body = entity.body,
                                mood = entity.mood?.let { Mood.entries.find { m -> m.name == it } },
                                location = entity.location,
                                weather = entity.weather,
                                tags = entity.tags?.split(",")?.map { t -> t.trim() } ?: emptyList(),
                                createdAt = entity.createdAt,
                                updatedAt = entity.updatedAt
                            )
                        )
                        count++
                    }
                    _state.update {
                        it.copy(
                            isSyncing = false,
                            syncMessage = "Imported $count entries",
                            scrollToTop = true
                        )
                    }
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isSyncing = false,
                            syncMessage = "Sync failed: ${e.message ?: e.javaClass.simpleName}"
                        )
                    }
                }
            )
        }
    }

    fun uploadToWebdav() {
        val settings = syncSettings
        if (settings.webdavUrl.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isSyncing = true, syncMessage = "Uploading journal...") }
            val content = exportToJrnl()
            if (content.isBlank()) {
                _state.update { it.copy(isSyncing = false, syncMessage = "No entries to upload") }
                return@launch
            }
            val result = webDavClient.uploadFile(
                baseUrl = settings.webdavUrl,
                username = settings.username,
                password = settings.password,
                filePath = settings.journalFilePath,
                content = content
            )

            result.fold(
                onSuccess = {
                    _state.update { it.copy(isSyncing = false, syncMessage = "Uploaded") }
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isSyncing = false,
                            syncMessage = "Upload failed: ${e.message ?: e.javaClass.simpleName}"
                        )
                    }
                }
            )
        }
    }

    fun exportToJrnl(): String {
        val entries = _state.value.entries.map { entry ->
            com.dale.jrnlmob.data.local.entity.EntryEntity(
                id = entry.id,
                dateTime = entry.dateTime,
                title = entry.title,
                body = entry.body,
                mood = entry.mood?.name,
                location = entry.location,
                weather = entry.weather,
                tags = if (entry.tags.isNotEmpty()) entry.tags.joinToString(",") else null,
                createdAt = entry.createdAt,
                updatedAt = entry.updatedAt
            )
        }
        return JrnlFileParser.format(entries)
    }

    fun dismissSyncMessage() {
        _state.update { it.copy(syncMessage = null) }
    }

    fun onScrolledToTop() {
        _state.update { it.copy(scrollToTop = false) }
    }
}
