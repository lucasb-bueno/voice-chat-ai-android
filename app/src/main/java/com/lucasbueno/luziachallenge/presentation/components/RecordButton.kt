package com.lucasbueno.luziachallenge.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RecordButton(
    isRecording: Boolean,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon: ImageVector = if (isRecording) Icons.Outlined.StopCircle else Icons.Outlined.Mic
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = if (isRecording) {
            MaterialTheme.colorScheme.errorContainer
        } else {
            MaterialTheme.colorScheme.primary
        },
        contentColor = if (isRecording) {
            MaterialTheme.colorScheme.onErrorContainer
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
