package com.dale.jrnlmob.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dale.jrnlmob.data.preferences.AppPreferences
import com.dale.jrnlmob.data.preferences.SyncSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

data class SettingsState(
    val webdavUrl: String = "",
    val username: String = "",
    val password: String = "",
    val journalPath: String = "jrnl.txt",
    val autoSync: Boolean = false,
    val temperatureUnit: String = "celsius",
    val isTesting: Boolean = false,
    val testResult: String? = null,
    val isSaved: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            preferences.syncSettings.collect { settings ->
                _state.update {
                    it.copy(
                        webdavUrl = settings.webdavUrl,
                        username = settings.username,
                        password = settings.password,
                        journalPath = settings.journalFilePath,
                        autoSync = settings.autoSync,
                        temperatureUnit = settings.temperatureUnit
                    )
                }
            }
        }
    }

    fun updateWebdavUrl(url: String) = _state.update { it.copy(webdavUrl = url, isSaved = false) }
    fun updateUsername(username: String) = _state.update { it.copy(username = username, isSaved = false) }
    fun updatePassword(password: String) = _state.update { it.copy(password = password, isSaved = false) }
    fun updateJournalPath(path: String) = _state.update { it.copy(journalPath = path, isSaved = false) }
    fun updateAutoSync(enabled: Boolean) = _state.update { it.copy(autoSync = enabled, isSaved = false) }
    fun updateTemperatureUnit(unit: String) = _state.update { it.copy(temperatureUnit = unit, isSaved = false) }

    fun save() {
        viewModelScope.launch {
            preferences.saveAll(
                SyncSettings(
                    webdavUrl = _state.value.webdavUrl,
                    username = _state.value.username,
                    password = _state.value.password,
                    journalFilePath = _state.value.journalPath,
                    autoSync = _state.value.autoSync,
                    temperatureUnit = _state.value.temperatureUnit
                )
            )
            _state.update { it.copy(isSaved = true) }
        }
    }

    fun testConnection() {
        viewModelScope.launch {
            _state.update { it.copy(isTesting = true, testResult = null) }
            val url = _state.value.webdavUrl.trimEnd('/')
            if (url.isBlank()) {
                _state.update { it.copy(testResult = "Enter a URL", isTesting = false) }
                return@launch
            }
            val username = _state.value.username
            val password = _state.value.password

            try {
                val result = withContext(Dispatchers.IO) {
                    val client = okhttp3.OkHttpClient.Builder()
                        .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                        .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                        .followRedirects(true)
                        .build()

                    val propfindBody = """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <d:propfind xmlns:d="DAV:">
                          <d:prop>
                            <d:resourcetype/>
                          </d:prop>
                        </d:propfind>
                    """.trimIndent()

                    val requestBody = propfindBody.toRequestBody(
                        "application/xml; charset=utf-8".toMediaType()
                    )

                    val req = okhttp3.Request.Builder()
                        .url(url)
                        .method("PROPFIND", requestBody)
                        .header("Content-Type", "application/xml; charset=utf-8")
                        .header("Depth", "1")
                        .apply {
                            if (username.isNotBlank()) {
                                header("Authorization", okhttp3.Credentials.basic(username, password))
                            }
                        }
                        .build()

                    val response = client.newCall(req).execute()
                    response.code to response.isSuccessful
                }

                val (code, successful) = result
                val msg = when {
                    successful -> "Connected"
                    code == 401 -> "Invalid credentials"
                    code == 403 -> "Access denied"
                    code == 404 -> "File not found — check journal path"
                    code == 405 -> "Server rejected PROPFIND"
                    code == 500 -> "Server error"
                    code == 502 -> "Bad gateway"
                    code == 503 -> "Server unavailable"
                    code in listOf(301, 302, 307, 308) -> "Redirected — use HTTPS or check URL"
                    else -> "HTTP $code"
                }
                _state.update { it.copy(testResult = msg, isTesting = false) }
                if (successful) {
                    save()
                }
            } catch (e: java.net.UnknownHostException) {
                _state.update { it.copy(testResult = "Host not found: check URL", isTesting = false) }
            } catch (e: java.net.ConnectException) {
                _state.update { it.copy(testResult = "Connection refused: check port", isTesting = false) }
            } catch (e: java.net.SocketTimeoutException) {
                _state.update { it.copy(testResult = "Timed out: server unreachable?", isTesting = false) }
            } catch (e: javax.net.ssl.SSLHandshakeException) {
                _state.update { it.copy(testResult = "SSL error — try http://", isTesting = false) }
            } catch (e: java.io.IOException) {
                _state.update { it.copy(testResult = "Network error: ${e.message}", isTesting = false) }
            } catch (e: Exception) {
                val msg = e.message ?: e.javaClass.simpleName
                _state.update { it.copy(testResult = "Error: $msg", isTesting = false) }
            }
        }
    }
}
