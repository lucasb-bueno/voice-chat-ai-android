package com.lucasbueno.voiceaichat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lucasbueno.voiceaichat.presentation.chat.ChatRoute
import com.lucasbueno.voiceaichat.ui.theme.VoiceAIChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoiceAIChatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatRoute()
                }
            }
        }
    }
}
