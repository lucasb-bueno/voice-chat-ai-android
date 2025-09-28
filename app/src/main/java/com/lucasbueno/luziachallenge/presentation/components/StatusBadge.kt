package com.lucasbueno.luziachallenge.presentation.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = {},
        enabled = false,
        label = { Text(text = text, style = MaterialTheme.typography.labelLarge) },
        modifier = modifier.semantics { contentDescription = text },
        colors = AssistChipDefaults.assistChipColors(
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Preview
@Composable
fun StatusBadgePreview() {
    StatusBadge(
        text =  "Transcribing audio..."
    )
}
