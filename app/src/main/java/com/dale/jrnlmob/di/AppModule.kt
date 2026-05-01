package com.dale.jrnlmob.di

import android.content.Context
import androidx.room.Room
import com.dale.jrnlmob.data.local.JrnlMobDatabase
import com.dale.jrnlmob.data.local.dao.EntryDao
import com.dale.jrnlmob.data.repository.JournalRepositoryImpl
import com.dale.jrnlmob.domain.repository.JournalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): JrnlMobDatabase {
        return Room.databaseBuilder(
            context,
            JrnlMobDatabase::class.java,
            "jrnl_mob.db"
        ).build()
    }

    @Provides
    fun provideEntryDao(database: JrnlMobDatabase): EntryDao {
        return database.entryDao()
    }

    @Provides
    @Singleton
    fun provideJournalRepository(impl: JournalRepositoryImpl): JournalRepository {
        return impl
    }
}
