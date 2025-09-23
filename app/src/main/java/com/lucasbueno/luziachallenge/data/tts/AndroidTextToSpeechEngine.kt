package com.lucasbueno.luziachallenge.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import com.lucasbueno.luziachallenge.domain.voice.VoiceSynthesizer
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class AndroidTextToSpeechEngine(
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : VoiceSynthesizer {

    private val readiness = CompletableDeferred<Boolean>()
    private lateinit var textToSpeech: TextToSpeech

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.getDefault()
                readiness.complete(true)
            } else {
                readiness.complete(false)
            }
        }
    }

    override suspend fun speak(text: String) {
        val isReady = readiness.await()
        if (!isReady) return
        withContext(dispatcher) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
        }
    }

    override fun stop() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
        }
    }

    override fun release() {
        if (!readiness.isCompleted) {
            readiness.complete(false)
        }
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}
