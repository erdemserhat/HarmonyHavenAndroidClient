package com.erdemserhat.harmonyhaven.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenBottomAppBarContainerColor

@Composable
fun CategoryRow(categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    val selectedCategoryId = remember { mutableStateOf(0) }
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedCategoryId.value == category.id,
                onItemClick = {
                    selectedCategoryId.value = category.id
                    onCategoryClick(category)
                }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onItemClick: () -> Unit,
    defaultColor: Color = Color.LightGray // Default color
) {

    val targetColor = if (isSelected) Color.White else Color.Black

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 400) // Adjust duration as needed
    )

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.5f)
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(
            //contentColor = if (isSelected) Color.White else Color.Black,
            //disabledContainerColor = if (isSelected) Color.White else Color.Black,
            containerColor = animatedColor,
            contentColor =  if (isSelected) Color.Black else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = category.name, style = MaterialTheme.typography.labelLarge)
        }
    }
}

data class Category(val id: Int, val name: String, val description: String)

@Preview(showBackground = true)
@Composable
private fun CategoriesRowSectionPreview() {

    val categories = listOf(
        Category(1, "a", "Telefonlar, bilgisayarlar, tabletler"),
        Category(2, "b", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "c", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "d", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "e", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "f", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "h", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(3, "g", "Romanlar, tarih kitapları, ders kitapları"),
        Category(4, "i", "Mutfak eşyaları, mobilya, dekorasyon"),
        Category(5, "j Ürünleri", "Koşu ayakkabısı, spor giyim, spor aksesuarları"),
        // ... diğer kategoriler ekleyebilirsiniz
    )

    CategoryRow(categories = categories) { selectedCategory ->
        // Handle category selection here
        println("Selected category: ${selectedCategory.name}")
    }
}
