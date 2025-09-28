# Luzia Android Challenge

An Android voice-to-AI chat sample implemented with a clean architecture stack, Jetpack Compose UI, Koin dependency injection, Retrofit networking, and Room-based caching.

## Project structure

```
app/
 └── src/main/java/com/lucasbueno/luziachallenge
     ├── data        # DTOs, Retrofit service, Room database, repository implementation
     ├── domain      # Pure Kotlin models, repository interface, and use cases
     ├── di          # Koin modules
     └── presentation
         ├── chat    # ViewModel, UI state, composable screens and navigation
         ├── components # Reusable Compose components
         └── MainActivity & Application bootstrap
```

Key technologies:

- **Clean Architecture**: Explicit separation between data, domain, and presentation layers.
- **MVVM**: `ChatViewModel` orchestrates use cases and exposes UI state flows.
- **Koin**: Provides dependency graph for networking, data sources, platform services, use cases, and presentation layer.
- **Retrofit + OkHttp**: OpenAI transcription and chat completion endpoints with an authorization interceptor.
- **Room**: Persists chat history with a configurable maximum cache size.
- **Jetpack Compose**: Accessible, responsive conversation UI with TalkBack-friendly semantics.
- **Media APIs**: `MediaRecorder` for voice capture and Android `TextToSpeech` for playback (bonus requirement).

## Getting started

1. **Configure secrets**

   Create or edit your root `local.properties` file and supply the API key and optional cache limit:

   ```properties
   OPENAI_API_KEY=sk-xxxx
   CHAT_CACHE_LIMIT=50
   ```

   The build will embed these values in `BuildConfig` for the runtime token provider and cache policy.

2. **Sync Gradle & build**

   ```bash
   ./gradlew build
   ```

3. **Run the app** from Android Studio (Device/Emulator with microphone access). When prompted, grant the microphone permission to enable audio recording.
   **If using Emulator, remember to grant access to microphone through Extend Controls -> Microphone -> Enable "Virtual Microphone uses host audio input"**

## Feature overview

- **Audio recording**: `AndroidAudioRecorder` wraps `MediaRecorder` and stores clips in the app cache.
- **Transcription & completion**: `ChatRepositoryImpl` streams audio to Whisper and sends conversation history to GPT-style chat completions via Retrofit.
- **Caching policy**: Room DAO enforces a configurable maximum number of stored messages, evicting the oldest entries when the limit is exceeded.
- **TalkBack support**: Message bubbles, toggles, and status badges expose descriptive content for screen readers.
- **Responsive UI**: Message widths scale with available space; status badges and scaffold adapt to phones or larger devices.
- **Text-to-Speech**: Optional playback of assistant replies through the `AndroidTextToSpeechEngine` and `SpeakTextUseCase`.

## Assumptions

- OpenAI endpoints are reachable and require a bearer token supplied via the provided API key.
- Whisper audio uploads accept the recorded `.m4a` format created by `MediaRecorder`.
- Minimal error UI (snackbars) is acceptable for initial delivery.

## Potential improvements

- Add automated tests (unit tests for use cases/repository, UI tests for Compose screen).
- Persist and restore user preferences (e.g., TTS toggle) via DataStore.
- Provide typed error mapping and localized user-facing messages.
- Stream partial transcriptions/responses for quicker feedback.
- Support manual text input alongside voice capture.
- Add more robust logging/analytics and crash reporting hooks.

## Accessibility notes

- Message content descriptions differentiate between user and assistant messages for TalkBack.
- UI controls provide descriptive labels and adapt to different widths, ensuring legibility and focus order.

## License

This project is provided as part of the Luzia Android technical challenge.
