package com.dale.jrnlmob.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dale.jrnlmob.data.local.entity.EntryEntity
import com.dale.jrnlmob.data.parser.JrnlFileParser
import com.dale.jrnlmob.data.preferences.AppPreferences
import com.dale.jrnlmob.data.sync.WebDavClient
import com.dale.jrnlmob.domain.model.JournalEntry
import com.dale.jrnlmob.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailState(
    val entry: JournalEntry? = null,
    val isLoading: Boolean = true,
    val isDeleted: Boolean = false,
    val syncError: String? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: JournalRepository,
    private val webDavClient: WebDavClient,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun loadEntry(entryId: Long) {
        viewModelScope.launch {
            repository.getEntryById(entryId).collect { entry ->
                _state.update { it.copy(entry = entry, isLoading = false) }
            }
        }
    }

    fun deleteEntry() {
        viewModelScope.launch {
            _state.value.entry?.let { entry ->
                repository.deleteEntry(entry)
                _state.update { it.copy(isDeleted = true) }
                try {
                    uploadJournal()
                } catch (e: Exception) {
                    _state.update { it.copy(syncError = "Deleted locally but sync failed: ${e.message}") }
                }
            }
        }
    }

    private suspend fun uploadJournal() {
        val settings = preferences.syncSettings.first()
        if (settings.webdavUrl.isBlank()) return
        val allEntries = repository.getAllEntries().first()

        val entities = allEntries.map { e ->
            EntryEntity(
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
        ).getOrThrow()
    }
}
