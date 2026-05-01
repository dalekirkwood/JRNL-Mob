package com.dale.jrnlmob.data.sync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebDavClient @Inject constructor() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    suspend fun downloadFile(
        baseUrl: String,
        username: String,
        password: String,
        filePath: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val url = "${baseUrl.trimEnd('/')}/${filePath.trimStart('/')}"
            val request = Request.Builder()
                .url(url)
                .apply {
                    if (username.isNotBlank()) {
                        header("Authorization", Credentials.basic(username, password))
                    }
                }
                .get()
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Result.success(response.body?.string() ?: "")
            } else {
                Result.failure(Exception("HTTP ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadFile(
        baseUrl: String,
        username: String,
        password: String,
        filePath: String,
        content: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val url = "${baseUrl.trimEnd('/')}/${filePath.trimStart('/')}"
            val body = content.toRequestBody("text/plain; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(url)
                .apply {
                    if (username.isNotBlank()) {
                        header("Authorization", Credentials.basic(username, password))
                    }
                }
                .put(body)
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
