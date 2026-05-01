package com.dale.jrnlmob.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dale.jrnlmob.data.local.dao.EntryDao
import com.dale.jrnlmob.data.local.entity.EntryEntity

@Database(entities = [EntryEntity::class], version = 1, exportSchema = false)
abstract class JrnlMobDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}
