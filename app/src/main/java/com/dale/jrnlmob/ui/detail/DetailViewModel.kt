package com.dale.jrnlmob.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dale.jrnlmob.domain.model.JournalEntry
import com.dale.jrnlmob.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailState(
    val entry: JournalEntry? = null,
    val isLoading: Boolean = true,
    val isDeleted: Boolean = false
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: JournalRepository
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
            }
        }
    }
}
