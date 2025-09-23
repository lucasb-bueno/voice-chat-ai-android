package com.lucasbueno.luziachallenge.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lucasbueno.luziachallenge.R
import com.lucasbueno.luziachallenge.presentation.chat.ChatMessageUiModel

@Composable
fun MessageBubble(
    message: ChatMessageUiModel,
    maxWidth: Dp,
    modifier: Modifier = Modifier
) {
    val background = if (message.isUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (message.isUser) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val alignment = if (message.isUser) TextAlign.End else TextAlign.Start
    val description = if (message.isUser) {
        stringResource(id = R.string.content_description_user_message, message.content)
    } else {
        stringResource(id = R.string.content_description_assistant_message, message.content)
    }

    Surface(
        modifier = modifier
            .widthIn(max = maxWidth)
            .semantics { contentDescription = description },
        color = background,
        contentColor = contentColor,
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = alignment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
        }
    }
}
