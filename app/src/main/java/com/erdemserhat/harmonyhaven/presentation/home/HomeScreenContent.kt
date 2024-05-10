package com.erdemserhat.harmonyhaven.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.appcomponents.home2.CategoriesRowSection
import com.erdemserhat.harmonyhaven.presentation.appcomponents.home2.MostReadHorizontalPager

@Composable
fun HomeScreenContent(
    categories: List<Category>,
    onCategorySelected: (category: Category) -> Unit,
    selectedCategory:Category,
    navController:NavController,
    recentArticles:List<ArticleResponseType>,
    categorisedArticles:List<ArticleResponseType>
) {
    Column(

        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.White
                    )
                )
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        //HarmonyHavenSearchBarPrototype2(Modifier)
        GreetingHarmonyHavenComponent()
        CategoriesRowSection(
            categoryList = categories,
            onCategorySelected = { category ->
                //selectedCategory = category
                //homeViewModel.getArticlesByCategoryId(category.id)
                onCategorySelected(category)
            },
            selectedCategory = selectedCategory


        )
        //
        MostReadHorizontalPager(navController, recentArticles)

        //HarmonyHavenSearchBarPrototype2(modifier = Modifier.padding(bottom = 20.dp))
        //MostReadHorizontalPagerDev(navController,homeState.articles)
        ArticleSection(
            navController = navController,
            articles = categorisedArticles,

            )

        //ContentGridShimmy()

    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val navController = rememberNavController()

    val categories = listOf(
        Category(id = 1, name = "Category 1", imagePath = "category_1_image_url"),
        Category(id = 2, name = "Category 2", imagePath = "category_2_image_url"),
        // Diğer kategoriler...
    )

    val selectedCategory = Category(id = 1, name = "Selected Category", imagePath = "selected_category_image_url")

    val recentArticles = listOf(
        ArticleResponseType(id = 1, title = "Recent Article 1", imagePath = "recent_article_1_image_url"),
        ArticleResponseType(id = 2, title = "Recent Article 2", imagePath = "recent_article_2_image_url"),
        // Diğer makaleler...
    )

    val categorisedArticles = listOf(
        ArticleResponseType(id = 1, title = "Article 1", imagePath = "article_1_image_url"),
        ArticleResponseType(id = 2, title = "Article 2", imagePath = "article_2_image_url"),
        // Diğer makaleler...
    )

    HomeScreenContent(
        categories = categories,
        onCategorySelected = {},
        selectedCategory = selectedCategory,
        navController = navController,
        recentArticles = recentArticles,
        categorisedArticles = categorisedArticles
    )
}


