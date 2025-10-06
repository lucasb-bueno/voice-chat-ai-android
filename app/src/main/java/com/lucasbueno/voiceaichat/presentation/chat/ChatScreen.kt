package com.lucasbueno.voiceaichat.presentation.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lucasbueno.voiceaichat.R
import com.lucasbueno.voiceaichat.domain.model.MessageAuthor
import com.lucasbueno.voiceaichat.presentation.components.MessageBubble
import com.lucasbueno.voiceaichat.presentation.components.RecordButton
import com.lucasbueno.voiceaichat.presentation.components.StatusBadge
import com.lucasbueno.voiceaichat.ui.theme.LuziaChallengeTheme

@Composable
fun ChatScreen(
    state: ChatUiState,
    snackbarHostState: SnackbarHostState,
    onRecordToggle: () -> Unit,
    onToggleTts: (Boolean) -> Unit,
    onClearConversation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recordContentDescription = stringResource(
        if (state.isRecording) R.string.content_description_stop_recording else R.string.content_description_start_recording
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ChatTopBar(onClearConversation = onClearConversation)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            ChatBottomBar(
                isRecording = state.isRecording,
                isTtsEnabled = state.isTtsEnabled,
                onRecordToggle = onRecordToggle,
                onToggleTts = onToggleTts,
                recordContentDescription = recordContentDescription
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val maxBubbleWidth = maxWidth * 0.85f
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                MessageList(
                    messages = state.messages,
                    maxBubbleWidth = maxBubbleWidth,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedVisibility(
                    visible = state.isLoadingTranscription,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    StatusBadge(
                        text = stringResource(id = R.string.status_transcribing),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                AnimatedVisibility(
                    visible = state.isLoadingResponse,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    StatusBadge(
                        text = stringResource(id = R.string.status_responding),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                AnimatedVisibility(
                    visible = state.isSynthesizing,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    StatusBadge(
                        text = stringResource(id = R.string.status_speaking),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatTopBar(onClearConversation: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            TextButton(onClick = onClearConversation) {
                Text(text = stringResource(id = R.string.action_clear_history))
            }
        }
    )
}

@Composable
private fun ChatBottomBar(
    isRecording: Boolean,
    isTtsEnabled: Boolean,
    onRecordToggle: () -> Unit,
    onToggleTts: (Boolean) -> Unit,
    recordContentDescription: String
) {
    val disableTextToSpeechLabel = stringResource(id = R.string.content_description_disable_tts)
    val enableTextToSpeechLabel = stringResource(id = R.string.content_description_enable_tts)

    Surface(shadowElevation = 4.dp) {
        BottomAppBar {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val useIcon = maxWidth < 360.dp

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (useIcon) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.label_tts),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = isTtsEnabled,
                            onCheckedChange = onToggleTts,
                            modifier = Modifier.semantics {
                                contentDescription = if (isTtsEnabled) {
                                    disableTextToSpeechLabel
                                } else {
                                    enableTextToSpeechLabel
                                }
                            }
                        )
                    }

                    RecordButton(
                        isRecording = isRecording,
                        contentDescription = recordContentDescription,
                        onClick = onRecordToggle
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageList(
    messages: List<ChatMessageUiModel>,
    maxBubbleWidth: Dp,
    modifier: Modifier = Modifier
) {
    if (messages.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.placeholder_empty_conversation),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = messages,
            key = { message -> message.id ?: message.timestamp }
        ) { message ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
            ) {
                MessageBubble(
                    message = message,
                    maxWidth = maxBubbleWidth
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    LuziaChallengeTheme {
        ChatScreen(
            state = ChatUiState(
                messages = listOf(
                    ChatMessageUiModel(
                        id = 1,
                        author = MessageAuthor.USER,
                        content = "Hello!",
                        timestamp = 1L,
                        isUser = true
                    ),
                    ChatMessageUiModel(
                        id = 2,
                        author = MessageAuthor.ASSISTANT,
                        content = "Hi there, how can I assist you today?",
                        timestamp = 2L,
                        isUser = false
                    )
                ),
                isTtsEnabled = true
            ),
            snackbarHostState = SnackbarHostState(),
            onRecordToggle = {},
            onToggleTts = {},
            onClearConversation = {}
        )
    }
}
