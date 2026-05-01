package com.dale.jrnlmob.domain.model

data class JournalEntry(
    val id: Long = 0,
    val dateTime: String,
    val title: String,
    val body: String,
    val mood: Mood? = null,
    val location: String? = null,
    val weather: String? = null,
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
