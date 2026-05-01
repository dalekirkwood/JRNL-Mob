package com.dale.jrnlmob.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dale.jrnlmob.data.local.entity.EntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY date_time DESC")
    fun getAllEntries(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getEntryById(id: Long): Flow<EntryEntity?>

    @Query("""
        SELECT * FROM entries 
        WHERE title LIKE '%' || :query || '%' 
        OR body LIKE '%' || :query || '%'
        OR location LIKE '%' || :query || '%'
        OR tags LIKE '%' || :query || '%'
        ORDER BY date_time DESC
    """)
    fun searchEntries(query: String): Flow<List<EntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryEntity): Long

    @Update
    suspend fun updateEntry(entry: EntryEntity)

    @Delete
    suspend fun deleteEntry(entry: EntryEntity)

    @Query("SELECT COUNT(*) FROM entries")
    suspend fun getEntryCount(): Int

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}
