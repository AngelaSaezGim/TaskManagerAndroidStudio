package com.angelasaez.taskmanager.common.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpacer(height: Int = 8, width: Int = 8, size: Int = 0) {
    val modifier = when {
        size > 0 -> Modifier.size(size.dp)
        // Altura y ancho
        height > 0 && width > 0 -> Modifier
            .height(height.dp)
            .width(width.dp)
        // Solo altura
        height > 8 -> Modifier.height(height.dp)
        // Solo ancho
        width > 0 -> Modifier.width(width.dp)
        else -> Modifier
    }
    Spacer(
        modifier = modifier
    )
}