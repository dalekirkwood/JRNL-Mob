package com.dale.jrnlmob.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class SyncSettings(
    val webdavUrl: String = "",
    val username: String = "",
    val password: String = "",
    val journalFilePath: String = "jrnl.txt",
    val autoSync: Boolean = false,
    val temperatureUnit: String = "celsius"
)

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val WEBDAV_URL = stringPreferencesKey("webdav_url")
        val USERNAME = stringPreferencesKey("webdav_username")
        val PASSWORD = stringPreferencesKey("webdav_password")
        val JOURNAL_PATH = stringPreferencesKey("journal_path")
        val AUTO_SYNC = booleanPreferencesKey("auto_sync")
        val TEMP_UNIT = stringPreferencesKey("temp_unit")
    }

    val syncSettings: Flow<SyncSettings> = context.dataStore.data.map { prefs ->
        SyncSettings(
            webdavUrl = prefs[Keys.WEBDAV_URL] ?: "",
            username = prefs[Keys.USERNAME] ?: "",
            password = prefs[Keys.PASSWORD] ?: "",
            journalFilePath = prefs[Keys.JOURNAL_PATH] ?: "jrnl.txt",
            autoSync = prefs[Keys.AUTO_SYNC] ?: false,
            temperatureUnit = prefs[Keys.TEMP_UNIT] ?: "celsius"
        )
    }

    suspend fun updateWebdavUrl(url: String) {
        context.dataStore.edit { it[Keys.WEBDAV_URL] = url }
    }

    suspend fun updateUsername(username: String) {
        context.dataStore.edit { it[Keys.USERNAME] = username }
    }

    suspend fun updatePassword(password: String) {
        context.dataStore.edit { it[Keys.PASSWORD] = password }
    }

    suspend fun updateJournalPath(path: String) {
        context.dataStore.edit { it[Keys.JOURNAL_PATH] = path }
    }

    suspend fun updateAutoSync(enabled: Boolean) {
        context.dataStore.edit { it[Keys.AUTO_SYNC] = enabled }
    }

    suspend fun updateTemperatureUnit(unit: String) {
        context.dataStore.edit { it[Keys.TEMP_UNIT] = unit }
    }

    suspend fun saveAll(settings: SyncSettings) {
        context.dataStore.edit {
            it[Keys.WEBDAV_URL] = settings.webdavUrl
            it[Keys.USERNAME] = settings.username
            it[Keys.PASSWORD] = settings.password
            it[Keys.JOURNAL_PATH] = settings.journalFilePath
            it[Keys.AUTO_SYNC] = settings.autoSync
            it[Keys.TEMP_UNIT] = settings.temperatureUnit
        }
    }
}
