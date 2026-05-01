# 📔 JRNL-Mob

> jrnl.sh in your pocket.

An Android companion for [jrnl](https://jrnl.sh) — the open-source command-line journaling tool. Write on your phone, sync via WebDAV to your own server, and read or edit the same journal from your terminal with `jrnl`. One journal, everywhere, in plain text.

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

## How it works

[jrnl](https://jrnl.sh) stores your journal as plain text files. JRNL-Mob reads and writes those same files, syncing through your WebDAV server.

```
Laptop:   jrnl ↔ ~/journal.txt  ──sync──  📁 Nextcloud / ownCloud
Phone:    JRNL-Mob ↔ journal.txt  ──sync── 📁 Nextcloud / ownCloud
```

Write on the bus. Edit on your laptop. Same file, same format.

## Features

🎭 Mood tracking with emoji
📍 GPS location tagging
🌤️ Automatic weather (Open-Meteo, no API key)
#️⃣ @tag syntax — compatible with jrnl's tags
🔍 Full-text search across all entries
🔄 WebDAV sync to your own server
📄 Plain text format — never locked in
🌙 Dark mode
🔒 Offline-first
🚫 Zero proprietary dependencies — 100% free software

## File Format

JRNL-Mob uses jrnl's timestamp format with an optional metadata line:

```
[2025-05-01 12:30:00 PM] Today was a great day @grateful
::jrnlmob mood=HAPPY location=New York weather=☀️ 22°C, Clear
```

The `::jrnlmob` line stores mood, location, weather, and tags. jrnl treats it as body text and carries on — both tools coexist in the same file without issues.

## Prerequisites

- [jrnl](https://jrnl.sh/en/stable/installation) installed on your computer
- A WebDAV server (Nextcloud, ownCloud, or any WebDAV host)
- Your journal file synced to that server

## Tech Stack

Jetpack Compose · Room · Hilt · OkHttp · Kotlin Coroutines · DataStore

## Building

```bash
git clone https://github.com/dalekirkwood/JRNL-Mob.git
cd JRNL-Mob
./gradlew assembleRelease
```

JDK 17 · Android SDK 36 · Gradle wrapper included

## License

GPL-3.0 — see [LICENSE](LICENSE)
