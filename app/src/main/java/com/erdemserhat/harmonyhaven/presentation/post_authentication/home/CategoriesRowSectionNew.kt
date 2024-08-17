package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.common.AppColors
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor
import com.erdemserhat.harmonyhaven.util.DefaultAppFont

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
                CategoryItem(
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

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {

    val targetColor =
        if (isSelected) harmonyHavenIndicatorColor else Color.Black


    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 400), label = "" // Adjust duration as needed
    )

    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(width = 90.dp, height = 45.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick() },

        colors = CardDefaults.cardColors(
            //contentColor = if (isSelected) Color.White else Color.Black,
            //disabledContainerColor = if (isSelected) Color.White else Color.Black,
            containerColor = animatedColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) Color.Black else Color.White

            )
        }
    }
}

@Preview(showBackground = true, uiMode = 2)
@Composable
private fun CategoriesRowSectionPreview() {

    val categories = listOf(
        Category(0, "Science", "Telefonlar, bilgisayarlar, tabletler"),
        Category(1, "Technology", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "Life", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(3, "Quotes", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(4, "Software", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(5, "Engineering", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(6, "Technology", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(7, "Quotes", "Romanlar, tarih kitapları, ders kitapları"),
        Category(8, "Science", "Mutfak eşyaları, mobilya, dekorasyon"),
        Category(9, "Quotes", "Koşu ayakkabısı, spor giyim, spor aksesuarları"),
        // ... diğer kategoriler ekleyebilirsiniz
    )




    Column(
        modifier = Modifier.fillMaxSize()
    ) {
       // CategoryRow(categories = categories) { selectedCategory ->
          //  // Handle category selection here
         //   println("Selected category: ${selectedCategory.name}")
      //  }

    }


}

@Composable
fun CategoriesPrototype() {

    val categories = listOf(
        Category(0, "Science", "Telefonlar, bilgisayarlar, tabletler"),
        Category(1, "Technology", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(2, "Life", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(3, "Quotes", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(4, "Software", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(5, "Engineering", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(6, "Technology", "Erkek giyim, kadın giyim, çocuk giyim"),
        Category(7, "Quotes", "Romanlar, tarih kitapları, ders kitapları"),
        Category(8, "Science", "Mutfak eşyaları, mobilya, dekorasyon"),
        Category(9, "Quotes", "Koşu ayakkabısı, spor giyim, spor aksesuarları"),
        // ... diğer kategoriler ekleyebilirsiniz
    )



    //Column(

 //   ) {
     //   CategoryRow(categories = categories) { selectedCategory ->
   //         // Handle category selection here
      //      println("Selected category: ${selectedCategory.name}")
      //  }

   // }

}
