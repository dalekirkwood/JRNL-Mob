package com.dale.jrnlmob.data.repository

import com.dale.jrnlmob.data.local.dao.EntryDao
import com.dale.jrnlmob.data.local.entity.EntryEntity
import com.dale.jrnlmob.domain.model.JournalEntry
import com.dale.jrnlmob.domain.model.Mood
import com.dale.jrnlmob.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val entryDao: EntryDao
) : JournalRepository {

    override fun getAllEntries(): Flow<List<JournalEntry>> {
        return entryDao.getAllEntries().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getEntryById(id: Long): Flow<JournalEntry?> {
        return entryDao.getEntryById(id).map { it?.toDomain() }
    }

    override fun searchEntries(query: String): Flow<List<JournalEntry>> {
        return entryDao.searchEntries(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveEntry(entry: JournalEntry): Long {
        val entity = entry.toEntity()
        return if (entry.id == 0L) {
            entryDao.insertEntry(entity)
        } else {
            entryDao.updateEntry(entity)
            entry.id
        }
    }

    override suspend fun deleteEntry(entry: JournalEntry) {
        entryDao.deleteEntry(entry.toEntity())
    }

    override suspend fun deleteAllEntries() {
        entryDao.deleteAll()
    }

    override suspend fun getEntryCount(): Int {
        return entryDao.getEntryCount()
    }

    private fun EntryEntity.toDomain(): JournalEntry {
        return JournalEntry(
            id = id,
            dateTime = dateTime,
            title = title,
            body = body,
            mood = mood?.let { Mood.entries.find { m -> m.name == it } },
            location = location,
            weather = weather,
            tags = tags?.split(",")?.map { it.trim() } ?: emptyList(),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun JournalEntry.toEntity(): EntryEntity {
        return EntryEntity(
            id = id,
            dateTime = dateTime,
            title = title,
            body = body,
            mood = mood?.name,
            location = location,
            weather = weather,
            tags = if (tags.isNotEmpty()) tags.joinToString(",") else null,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
