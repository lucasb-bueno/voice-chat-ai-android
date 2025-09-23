package com.lucasbueno.luziachallenge.domain.audio

import java.io.File

interface AudioRecorder {
    suspend fun startRecording(): File
    suspend fun stopRecording(): File?
    val isRecording: Boolean
}
