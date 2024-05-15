package com.erdemserhat.harmonyhaven.presentation.home

import android.util.Log
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.appcomponents.items
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.util.AppColors
import com.erdemserhat.harmonyhaven.util.DefaultAppFont
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalSnapperApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreenContentNew(
    navController: NavController,
    articles: List<ArticleResponseType>,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {

    var isFocusedSearchBar by remember {
        mutableStateOf(false)
    }

    var query by remember {
        mutableStateOf("")
    }

    var isKeyboardVisible by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        item {
            Spacer(modifier = Modifier.size(20.dp))
            SearchBarWithIcon(
                modifier = Modifier.fillMaxWidth(0.9f),
                onActiveChange = { isFocusedSearchBar = it },
                query = query,
                onQueryChange = { query = it }
            )
            Spacer(modifier = Modifier.size(20.dp))

        }

        item {
            isKeyboardVisible = WindowInsets.isImeVisible
        }

        if(isFocusedSearchBar && isKeyboardVisible){
            items(articles){filteredArticles->
                Column() {
                    //List here
                    SearchingItem(filteredArticles)
                    Spacer(modifier = Modifier.size(10.dp))


                }



            }


        }else{
            item {
                CategoryRow(categories = categories) { selectedCategory ->
                    onCategorySelected(selectedCategory)

                }
            }

            items(articles) { article ->
                Article(
                    article,
                    onReadButtonClicked ={navController.navigate(Screen.Article.route)}

                )



            }


        }

    }



}



@Preview(showBackground = true)
@Composable
private fun HomeScreenContentNewPreview() {
    //HomeScreenContentNew()

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        //search bar
        androidx.compose.material3.SearchBar(
            colors = SearchBarDefaults.colors(
                containerColor = Color(0xFFE0E0E0),
                dividerColor = Color(0xFFE0E0E0)

            ),
            shape = RoundedCornerShape(10.dp),

            query = text,
            onQueryChange = { text = it },
            onSearch = { /*active = false*/ },
            active = false,
            onActiveChange = {
                onActiveChange(it)
            },
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
            //trailingIcon = { Icon(imageVector = Icons.Default.MoreVert, contentDescription = null) }

        ) {

        }


    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchBarWithIcon(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {}
) {
    val colors = androidx.compose.material3.TextFieldDefaults.colors(
        cursorColor = Color.Red,
        disabledContainerColor = Color.Red,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedLabelColor = Color.Red,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    )
    var isKeyboardOpen by remember { mutableStateOf(false) }



    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.serch1),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(24.dp),
            //colorFilter = ColorFilter.tint(Color.Red) // İstediğiniz renk


        )
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search...") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            modifier = Modifier
                .background(Color.Transparent)
                .onFocusChanged {
                    onActiveChange(it.isFocused)

                },
            colors = colors
        )


    }
}


@Composable
fun Article(
    article: ArticleResponseType,
    modifier: Modifier = Modifier,
    onReadButtonClicked: () -> Unit = {},

    ) {
    var shouldShowShimmer by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(15.dp),

                )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, radius = 100.dp)
            ) { onReadButtonClicked() },
        contentAlignment = Alignment.Center

    ) {
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .fillMaxWidth(0.9f)// Köşeleri yuvarla ve 16dp'lik yarıçapa sahip olacak şekilde kırp

        ) {

            AsyncImage(
                model = article.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .aspectRatio(2f) // // Genişlik / Yükseklik oranı 1.5
                    .align(Alignment.CenterHorizontally),
                onSuccess = { shouldShowShimmer = false },
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "The future of backend is Kotlin",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = DefaultAppFont,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .align(Alignment.Start),
                overflow = TextOverflow.Ellipsis
            )



            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lobortis venenatis nulla quis lobortis. Vivamus pharetra odio id lectus tristique, ac fermentum odio eleifend. Morbi quis mattis nisi, eget euismod odio.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = DefaultAppFont,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Start),
                overflow = TextOverflow.Ellipsis
            )

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black

                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 5.dp)
                    .background(color = Color.Transparent)
            ) {
                Text(
                    text = "Read More...",
                    fontFamily = DefaultAppFont,
                    fontWeight = FontWeight.Bold

                )
            }

        }


    }

}
/**

@Composable
fun AppDivider() {
Box(
modifier = Modifier
.height(0.5.dp)
.fillMaxWidth(1f)
.background(color = Color.Black) // Çizgi rengi
)

}

@Composable
fun RecentArticlesSection(modifier: Modifier = Modifier) {
Column(
horizontalAlignment = Alignment.CenterHorizontally,
modifier = modifier
.fillMaxWidth(1f)
) {
Spacer(modifier = Modifier.size(10.dp))

AppDivider()


RecentArticlesFlow(
modifier = Modifier.fillMaxWidth(0.9f),
navController = rememberNavController(),
articleList = generateMockArticles(5)
)

AppDivider()


}

}


@Composable
fun CategoryFlow() {

LazyColumn(
modifier = Modifier
.verticalScroll(rememberScrollState())
) {

}
}
@Composable
fun B(
article: ArticleResponseType,
modifier: Modifier = Modifier,
onReadButtonClicked: () -> Unit,

) {
var shouldShowShimmer by rememberSaveable {
mutableStateOf(true)
}

Column(
modifier = modifier,
horizontalAlignment = Alignment.CenterHorizontally
) {

Box(
modifier = Modifier
.size(width = 350.dp, height = 360.dp)
.padding(10.dp)
.background(
color = Color.Transparent,
shape = RoundedCornerShape(15.dp),

)
.border(width = 0.1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
.clickable(
interactionSource = remember { MutableInteractionSource() },
indication = rememberRipple(bounded = true, radius = 100.dp)
) { onReadButtonClicked() },
contentAlignment = Alignment.Center

) {
Column(
modifier = Modifier.fillMaxSize(),
) {


AsyncImage(
model = article.imagePath,
contentDescription = null,
modifier = Modifier
.size(height = 200.dp, width = 330.dp)
.padding(10.dp)
.align(Alignment.CenterHorizontally)
.clip(shape = RoundedCornerShape(16.dp)),
onSuccess = { shouldShowShimmer = false },
contentScale = ContentScale.FillBounds
)

Text(
text = "The future of backend is Kotlin",
fontSize = 20.sp,
fontWeight = FontWeight.Bold,
fontFamily = DefaultAppFont,
color = harmonyHavenDarkGreenColor,
modifier = Modifier
.padding(start = 10.dp)
.align(Alignment.Start),
overflow = TextOverflow.Ellipsis
)

Text(
text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lobortis venenatis nulla quis lobortis. Vivamus pharetra odio id lectus tristique, ac fermentum odio eleifend. Morbi quis mattis nisi, eget euismod odio.",
fontSize = 14.sp,
fontWeight = FontWeight.Normal,
fontFamily = DefaultAppFont,
color = harmonyHavenDarkGreenColor,
modifier = Modifier
.padding(10.dp)
.align(Alignment.Start),
overflow = TextOverflow.Ellipsis
)


}


}


}

}

@Composable
fun QuoteOfDay(modifier: Modifier = Modifier, quote: String, writer: String) {
Column(
modifier = modifier.fillMaxWidth(0.9f),
horizontalAlignment = Alignment.CenterHorizontally
) {
//Quote
Text(
text = quote,
fontFamily = DefaultAppFont,
fontWeight = FontWeight.SemiBold,
fontSize = 15.sp,
modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 25.dp),
textAlign = TextAlign.Center,

)

//Writer

Text(
text = writer,
fontFamily = DefaultAppFont,
fontWeight = FontWeight.SemiBold,
fontSize = 15.sp,
modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 15.dp),
textAlign = TextAlign.Center,

)


}

}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentArticlesFlow(
modifier: Modifier = Modifier,
articleList: List<ArticleResponseType>,
navController: NavController,
) {

val pagerState = rememberPagerState(pageCount = {
4//list size
})
LaunchedEffect(Unit) {
while (true) {
kotlinx.coroutines.delay(5000) // Değişim aralığı, burada 5 saniye olarak belirtilmiştir. İstediğiniz süreyi ayarlayabilirsiniz.
pagerState.animateScrollToPage(
(pagerState.currentPage + 1) % pagerState.pageCount,
animationSpec = TweenSpec(durationMillis = 1000)
)
}
}

Column(
modifier = modifier,
horizontalAlignment = Alignment.CenterHorizontally,
) {

Text(
text = "Recent Articles",
fontFamily = DefaultAppFont,
color = androidx.compose.material.MaterialTheme.AppColors.secondary,
fontWeight = FontWeight.Bold,
fontSize = 24.sp,
modifier = Modifier
.padding(start = 25.dp, end = 25.dp, top = 25.dp)
.align(Alignment.Start),
textAlign = TextAlign.Center,

)



HorizontalPager(
state = pagerState
) { page ->
val articleId = articleList[page].id

Article(
article = articleList[page], modifier = Modifier.fillMaxSize()
) { navController.navigate(Screen.Article.route + "/$articleId") }


}

}
}
 */

@Preview(showBackground = true)
@Composable
private fun SearchingItemPreview() {
    SearchingItem(generateMockArticles(1)[0])
}

@Composable
fun SearchingItem(
    article: ArticleResponseType
) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.1f)
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {  }
                    

            
            ){
                AsyncImage(
                    model = article.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.3f)
                        .clip(shape = RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp))
                        .aspectRatio(1.5f) // // Genişlik / Yükseklik oranı 1.5
                    ,
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = article.title,
                        fontFamily = DefaultAppFont,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Start).padding(start = 5.dp,end=5.dp)


                    )
                    Text(
                        text = article.content,
                        fontFamily = DefaultAppFont,
                        fontWeight = FontWeight.ExtraLight,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 5.dp,end=5.dp),
                        maxLines = 3


                    )
                }
            }


}

fun generateMockArticles(count: Int): List<ArticleResponseType> {
    val articles = mutableListOf<ArticleResponseType>()

    repeat(count) {
        val id = it + 1
        val title = "Title $id"
        val content = "Content $id"
        val publishDate = "2024-05-11" // Bu tarihi istediğiniz gibi ayarlayabilirsiniz
        val category = "Category $id"
        val imagePath =
            "https://images.unsplash.com/photo-1543373014-cfe4f4bc1cdf?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aGlnaCUyMHJlc29sdXRpb258ZW58MHx8MHx8fDA%3D" // Örnek bir resim yolunu belirtin

        articles.add(
            ArticleResponseType(
                id = id,
                title = title,
                content = content,
                publishDate = publishDate,
                category = category,
                imagePath = imagePath
            )
        )
    }

    return articles
}
