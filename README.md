# JRNL-Mob

A minimalist journaling app for Android with WebDAV sync.

## Features

- Write journal entries with mood, weather, and location
- Entries stored in a simple plain-text `.jrnl` file format
- Sync entries across devices via WebDAV (Nextcloud, ownCloud, etc.)
- Completely offline-first: all data stored locally
- Tag entries with `@tag` syntax
- Automatic weather lookup via Open-Meteo API
- Material 3 design with Jetpack Compose

## File Format

Entries are stored as a plain text file (`jrnl.txt` by default) in a human-readable format:

```
[2026-05-01 12:30:00 PM] Today was a great day @grateful
::jrnlmob mood=HAPPY location=New York weather=☀️ 22°C, Clear
```

The `::jrnlmob` metadata line stores mood, location, weather, and tags.

## Building

```bash
./gradlew assembleDebug
```

### Requirements

- Android SDK 36
- JDK 17
- Gradle 9.x (wrapper included)

## License

GPL-3.0 — see [LICENSE](LICENSE)
