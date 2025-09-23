package com.lucasbueno.luziachallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lucasbueno.luziachallenge.presentation.chat.ChatRoute
import com.lucasbueno.luziachallenge.ui.theme.LuziaChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuziaChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatRoute()
                }
            }
        }
    }
}
