package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArticleCustomizationSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = ((valueRange.endInclusive - valueRange.start) / 2).toInt()
        )
    }
}

@Composable
fun ArticleFontFamilySelector(
    selectedFont: String,
    onFontSelected: (String) -> Unit
) {
    val fontFamilies = listOf(
        "Default",
        "Serif",
        "Monospace",
        "Sans Serif"
    )

    Column {
        Text(
            text = "Font Family",
            style = MaterialTheme.typography.bodyMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(fontFamilies) { font ->
                FontFamilyChip(
                    fontName = font,
                    isSelected = font == selectedFont,
                    onSelect = { onFontSelected(font) }
                )
            }
        }
    }
}

@Composable
private fun FontFamilyChip(
    fontName: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onSelect),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                else MaterialTheme.colorScheme.surface,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text = fontName,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ArticleBackgroundColorPicker(
    selectedColor: Color,
    onColorSelected: (android.graphics.Color) -> Unit
) {
    val colors = listOf(
        Color.White,
        Color(0xFFF5F5F5), // Light gray
        Color(0xFFE8F5E9), // Light green
        Color(0xFFFFF3E0), // Light orange
        Color(0xFFE3F2FD)  // Light blue
    )

    Column {
        Text(
            text = "Background Color",
            style = MaterialTheme.typography.bodyMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(colors) { color ->
                ColorCircle(
                    color = color,
                    isSelected = color == selectedColor,
                    onSelect = {/* onColorSelected(color) */}
                )
            }
        }
    }
}

@Composable
private fun ColorCircle(
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable(onClick = onSelect)
    )
} 