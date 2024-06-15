package com.altayiskender.movieapp.ui.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenreChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.border(
            1.dp,
            color = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        )
    ) {
        Text(text = text, Modifier.padding(8.dp))
    }
}
