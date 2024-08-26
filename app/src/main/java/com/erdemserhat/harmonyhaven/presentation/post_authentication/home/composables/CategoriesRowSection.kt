package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.CategoryCard
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont

@Composable
fun CategoryRow(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    selectedCategoryId:Int,


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

            items(categories) { category ->
                CategoryCard(
                    category = category,
                    isSelected = selectedCategoryId == category.id,
                    onItemClick = {
                        onCategoryClick(category)
                    }
                )
            }
        }


    }

}

