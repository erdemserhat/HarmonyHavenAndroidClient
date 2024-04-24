package com.erdemserhat.harmonyhaven.presentation.appcomponents.home2

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenBottomAppBarContainerColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.util.customFontInter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CategoriesRowSection(
    categoryList: List<Category>,
    onCategorySelected: (Category) -> Unit,
    selectedCategory: Category
) {
    if(categoryList.isNotEmpty()) {
        Column {

            Spacer(modifier = Modifier.height(25.dp))
            androidx.compose.material3.Text(
                text = "Categories",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontFamily = customFontInter,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenTitleTextColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                for (category in categoryList) {
                    Spacer(modifier = Modifier.width(15.dp))
                    CategoriesRowSectionElement(category, onCategorySelected, selectedCategory)
                }
            }

        }
    }else{
        Column {

            Spacer(modifier = Modifier.height(25.dp))
            androidx.compose.material3.Text(
                text = "Categories",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontFamily = customFontInter,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenTitleTextColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                repeat(10) {
                    Spacer(modifier = Modifier.width(15.dp))
                    CategoriesRowSectionElementShimmer()
                }
            }

        }





    }
}

@Composable
fun CategoriesRowSectionElement(
    category: Category,
    onCategorySelected: (Category) -> Unit,
    selectedCategory: Category,

) {
    var showShimmer = remember { mutableStateOf(true) }

    // Seçili kategori ile mevcut kategori arasında karşılaştırma yaparak sınır rengini belirle
    val borderColor =
        if (category == selectedCategory) harmonyHavenBottomAppBarContainerColor else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)

                .border(BorderStroke(2.dp, borderColor), shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, radius = 40.dp)
                ) {
                    onCategorySelected(category)


                }
                .background(
                    brush = shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = showShimmer.value,
                        gradiantVariantFirst = harmonyHavenComponentWhite,
                        gradiantVariantSecond = harmonyHavenComponentWhite
                    ),
                    shape = CircleShape,


                    ),

            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = category.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .graphicsLayer {
                        shape = CircleShape
                    },
                onSuccess = { showShimmer.value = false },
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = category.name,
            textAlign = TextAlign.Justify,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Content(
    category: Category,
    onCategoryClicked: (Category) -> Unit = {}

) {
    val showShimmer = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = showShimmer.value,
                    gradiantVariantFirst = harmonyHavenComponentWhite,
                    gradiantVariantSecond = harmonyHavenComponentWhite

                ),
                shape = RoundedCornerShape(25.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, radius = 75.dp)
            ) { onCategoryClicked(category) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = category.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
                onSuccess = { showShimmer.value = false },
                contentScale = ContentScale.Inside
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = category.name,
                fontFamily = customFontInter,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.labelLarge.fontSize

            )
        }

    }

}

@Composable
fun CategoriesRowSectionElementShimmer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
                .background(
                    brush = shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = true,
                        gradiantVariantFirst = harmonyHavenComponentWhite,
                        gradiantVariantSecond = harmonyHavenComponentWhite
                    ),
                    shape = CircleShape,


                    ),

            contentAlignment = Alignment.Center
        ) {

        }

    }
}



