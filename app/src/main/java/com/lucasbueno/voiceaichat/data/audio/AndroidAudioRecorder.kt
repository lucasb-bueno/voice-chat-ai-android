package com.lucasbueno.voiceaichat.data.audio

import android.content.Context
import android.media.MediaRecorder
import com.lucasbueno.voiceaichat.domain.audio.AudioRecorder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AndroidAudioRecorder(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null

    override val isRecording: Boolean
        get() = recorder != null

    override suspend fun startRecording(): File = withContext(dispatcher) {
        stopRecorder()
        val directory = File(context.cacheDir, "recordings").apply { mkdirs() }
        val file = File.createTempFile("recording_", ".m4a", directory)
        val mediaRecorder = MediaRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128_000)
            setAudioSamplingRate(44_100)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }
        recorder = mediaRecorder
        outputFile = file
        file
    }

    override suspend fun stopRecording(): File? = withContext(dispatcher) {
        val file = outputFile
        try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
            file
        } catch (error: RuntimeException) {
            file?.delete()
            null
        } finally {
            recorder = null
            outputFile = null
        }
    }

    private fun stopRecorder() {
        try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
        } catch (_: RuntimeException) {
            outputFile?.delete()
        } finally {
            recorder = null
            outputFile = null
        }
    }
}
