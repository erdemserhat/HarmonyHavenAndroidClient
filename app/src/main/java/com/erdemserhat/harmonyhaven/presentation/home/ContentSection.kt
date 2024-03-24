package com.erdemserhat.harmonyhaven.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContentSection(
    viewmodel: HomeViewModel = hiltViewModel()
) {
    val homeState by viewmodel.homeState.collectAsState()


    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),

        ) {
        Spacer(modifier = Modifier.height(30.dp))
        
        Text(
            text = "Categories",
            modifier = Modifier
                .padding(start = 16.dp),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenTitleTextColor,
            fontSize = MaterialTheme.typography.titleLarge.fontSize

        )

        if (homeState.isCategoryReady)
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
fun ContentGrid(categories: List<Category>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
    ) {
        for (index in categories.indices) {
            Spacer(modifier = Modifier.width(10.dp))
            Content(categories[index])

        }
    }
}




