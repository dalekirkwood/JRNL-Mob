package com.dale.jrnlmob.domain.repository

import com.dale.jrnlmob.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllEntries(): Flow<List<JournalEntry>>
    fun getEntryById(id: Long): Flow<JournalEntry?>
    fun searchEntries(query: String): Flow<List<JournalEntry>>
    suspend fun saveEntry(entry: JournalEntry): Long
    suspend fun deleteEntry(entry: JournalEntry)
    suspend fun deleteAllEntries()
    suspend fun getEntryCount(): Int
}
