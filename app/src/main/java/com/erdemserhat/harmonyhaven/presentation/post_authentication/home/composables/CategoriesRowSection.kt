package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.CategoryCard
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun CategoryRow(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    selectedCategoryId:Int,
    isReady: Boolean,


) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Kategoriler",
            fontFamily = DefaultAppFont,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start),
            textAlign = TextAlign.Center,

            )

        Spacer(modifier = Modifier.size(15.dp))


        LazyRow(
            modifier =
            Modifier
                .fillMaxWidth(0.9f)
                .height(70.dp)


        ) {

            if (isReady) {

                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        isSelected = selectedCategoryId == category.id,
                        onItemClick = {
                            onCategoryClick(category)
                        }
                    )
                }

            } else {
                items(5) { // Replace `5` with the number of shimmer items you want
                    ShimmerCategoryCard()
                }

            }


        }

    }


}



@Composable
fun ShimmerCategoryCard(onItemClick: () -> Unit={}) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(width = 90.dp, height = 45.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick() }
            .placeholder(
                visible = true,
                color = Color.LightGray,  // Base color of the shimmer effect
                shape = RoundedCornerShape(10.dp),  // Match the shape of the card
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White  // Color of the shimmer highlight
                )
            ),
        elevation = CardDefaults.cardElevation(4.dp)  // Use CardDefaults to specify elevation
    ) {
        // Content of the card can be added here if needed
    }
}