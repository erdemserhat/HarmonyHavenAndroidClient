package com.erdemserhat.harmonyhaven.presentation.home
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.home.components.ContentGridShimmy
import com.erdemserhat.harmonyhaven.presentation.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.util.customFontInter




@Composable
fun ContentSection(
    viewmodel: HomeViewModel = hiltViewModel()
) {


    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),

    ) {
        Text(
            text = "Contents",
            modifier = Modifier
                .padding(start = 16.dp),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenTitleTextColor,
            fontSize = MaterialTheme.typography.titleMedium.fontSize

        )

        if(viewmodel.homeState.value.isCategoryReady)
            ContentGrid(viewmodel.homeState.value.categories)
        else
            ContentGridShimmy()



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
            .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value), shape = RoundedCornerShape(25.dp))
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
                contentScale = ContentScale.Fit
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
fun ContentGrid(categories:List<Category>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        for (index in categories.indices) {
            if (index % 2 == 0) {
                Row {
                    Content(categories[index])
                    Spacer(modifier = Modifier.width(8.dp))
                    if (index + 1 < categories.size) {
                        Content(categories[index + 1])
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}




