package com.dale.jrnlmob.data.parser

import com.dale.jrnlmob.data.local.entity.EntryEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object JrnlFileParser {

    private const val TAG = "JrnlFileParser"

    private val timestampPattern = Regex(
        """^\[(\d{4}-\d{2}-\d{2})\s+(\d{1,2}:\d{2}(?::\d{2})?)\s*([APap][Mm])?]\s*(.*)"""
    )
    private val metaPattern = Regex("""^::jrnlmob\s+(.+)""")
    private val kvPattern = Regex("""(\w+)=(.+?)(?=\s+(?:mood|location|weather|tags)=|$)""")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val time24Formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val time12Formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
    private val time12NoSecFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    private val exportFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")

    fun parse(content: String): List<EntryEntity> {
        val entries = mutableListOf<EntryEntity>()
        val lines = content.lines()

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trimEnd()
            val match = timestampPattern.find(line)

            if (match != null) {
                val datePart = match.groupValues[1]
                val rawTime = match.groupValues[2]
                val ampm = match.groupValues[3].takeIf { it.isNotBlank() }
                val firstLine = match.groupValues[4]

                val timeFormatted = normalizeTime(rawTime, ampm)
                val dateTimeStr = "$datePart $timeFormatted"

                val dateTime = try {
                    LocalDateTime.of(
                        LocalDate.parse(datePart, dateFormatter),
                        LocalTime.parse(timeFormatted, time24Formatter)
                    )
                } catch (_: Exception) { null }

                val bodyLines = mutableListOf<String>()
                if (firstLine.isNotBlank()) bodyLines.add(firstLine)

                i++
                var mood: String? = null
                var location: String? = null
                var weather: String? = null
                var tagsFromMeta: String? = null

                while (i < lines.size) {
                    val nextLine = lines[i]
                    if (timestampPattern.find(nextLine) != null) break
                    val metaMatch = metaPattern.find(nextLine.trimEnd())
                    if (metaMatch != null) {
                        val metaStr = metaMatch.groupValues[1]
                        kvPattern.findAll(metaStr).forEach { kv ->
                            when (kv.groupValues[1]) {
                                "mood" -> mood = kv.groupValues[2].trim()
                                "location" -> location = kv.groupValues[2].trim()
                                "weather" -> weather = kv.groupValues[2].trim()
                                "tags" -> tagsFromMeta = kv.groupValues[2].trim()
                            }
                        }
                        i++
                        break
                    }
                    if (nextLine.isBlank() && i + 1 < lines.size &&
                        timestampPattern.find(lines[i + 1]) != null
                    ) {
                        i++
                        break
                    }
                    bodyLines.add(nextLine.trimEnd())
                    i++
                }

                val body = bodyLines.joinToString("\n").trim()
                val title = firstLine.ifBlank { body.take(80) }
                val bodyTags = Regex("@\\w+").findAll(body)
                    .map { it.value.removePrefix("@") }.toList()
                val metaTags = tagsFromMeta?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() } ?: emptyList()
                val tags = (metaTags + bodyTags).distinct()

                android.util.Log.d(TAG, "parsed entry: title=$title mood=$mood location=$location weather=$weather tags=$tags")

                entries.add(
                    EntryEntity(
                        dateTime = dateTimeStr,
                        title = title,
                        body = body.ifBlank { firstLine },
                        mood = mood,
                        location = location,
                        weather = weather,
                        tags = if (tags.isNotEmpty()) tags.joinToString(",") else null,
                        createdAt = dateTime?.atZone(java.time.ZoneId.systemDefault())
                            ?.toInstant()?.toEpochMilli() ?: System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            } else {
                i++
            }
        }

        return entries
    }

    private fun normalizeTime(rawTime: String, ampm: String?): String {
        if (ampm == null) {
            val parts = rawTime.split(":")
            val hour = parts[0].padStart(2, '0')
            val min = parts[1]
            val sec = if (parts.size > 2) ":${parts[2].padStart(2, '0')}" else ":00"
            return "$hour:$min$sec"
        }
        val fullTime = if (rawTime.count { it == ':' } >= 2) rawTime else "$rawTime:00"
        return try {
            LocalTime.parse("$fullTime $ampm", time12Formatter).format(time24Formatter)
        } catch (_: Exception) {
            try {
                LocalTime.parse("$rawTime $ampm", time12NoSecFormatter).format(time24Formatter)
            } catch (_: Exception) { rawTime }
        }
    }

    fun format(entries: List<EntryEntity>): String {
        val sb = StringBuilder()
        val sortedEntries = entries.sortedBy { it.dateTime }

        for ((index, entry) in sortedEntries.withIndex()) {
            val dtStr = entry.dateTime
            val datePart = dtStr.take(10)
            val timePart = if (dtStr.length > 11) dtStr.substring(11) else "00:00:00"
            val date = try { LocalDate.parse(datePart, dateFormatter) } catch (_: Exception) { null }
            val time = try {
                if (timePart.count { it == ':' } >= 2) {
                    LocalTime.parse(timePart, time24Formatter)
                } else {
                    LocalTime.parse("$timePart:00", time24Formatter)
                }
            } catch (_: Exception) { null }
            val ldt = if (date != null && time != null) LocalDateTime.of(date, time) else null

            val displayTs = ldt?.format(exportFormatter) ?: "$datePart $timePart"
            sb.append("[$displayTs] ${entry.body}")

            val metas = mutableListOf<String>()
            entry.mood?.let { metas.add("mood=$it") }
            entry.location?.let { metas.add("location=$it") }
            entry.weather?.let { metas.add("weather=$it") }
            entry.tags?.takeIf { it.isNotBlank() }?.let { metas.add("tags=$it") }
            if (metas.isNotEmpty()) {
                sb.append("\n::jrnlmob ${metas.joinToString(" ")}")
            }

            if (index < sortedEntries.lastIndex) {
                sb.append("\n\n")
            }
        }

        return sb.toString()
    }
}
