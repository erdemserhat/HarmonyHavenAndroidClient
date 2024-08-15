package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.about_us

import LocalGifImage
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.MinimizedArticleItem
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.MockSavedArticles
import dev.jeziellago.compose.markdowntext.MarkdownText


var mockData ="# About Us\n" +
        "\n" +
        "## Welcome to Harmony Haven\n" +
        "\n" +
        "Harmony Haven is a **personalized development and motivation app** designed to *inspire* and *empower* individuals on their journey of self-improvement. Our mission is to provide insightful articles and resources that foster personal growth and motivation, helping users become the best version of themselves.\n" +
        "\n" +
        "## Our Team\n" +
        "\n" +
        "Meet the passionate individuals who drive Harmony Haven forward:\n" +
        "\n" +
        "### Serhat Erdem\n" +
        "**Founder & CEO**  \n" +
        "**Bio:** Serhat is the driving force behind Harmony Haven. As an experienced **Android developer**, he brings innovative ideas to life, shaping the app's direction and ensuring a seamless user experience.\n" +
        "\n" +
        "### Ahmet Suat Can\n" +
        "**iOS Developer**  \n" +
        "**Bio:** Ahmet is our dedicated **iOS developer**, committed to creating engaging experiences for Apple users. With his expertise, he ensures that Harmony Haven is optimized for iOS devices, delivering a smooth and intuitive experience.\n" +
        "\n" +
        "### Samet Berkant Koca\n" +
        "**Backend Developer**  \n" +
        "**Bio:** Samet is our **backend wizard**, architecting the systems that power Harmony Haven's functionality. With his skills, he ensures that the app's backend is robust, secure, and scalable.\n" +
        "\n" +
        "### Yavuz Samet Kan\n" +
        "**Web Developer**  \n" +
        "**Bio:** Yavuz Samet specializes in **web development**, bringing Harmony Haven to life on the web. With his expertise, he ensures that the app is accessible to users across all platforms.\n" +
        "\n" +
        "## Join Us on Our Journey\n" +
        "\n" +
        "Thank you for choosing Harmony Haven. Together, we can embark on a journey of self-discovery and personal growth.\n"
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AboutUsScreen(navController: NavController) {
    val homeViewmodel: HomeViewModel = hiltViewModel()
    AboutUsScreenContent(navController)

}

@Preview
@Composable
private fun AboutUsScreenContentPreview() {
    AboutUsScreenContent(rememberNavController())
    
}

@Composable
fun AboutUsScreenContent(
    navController: NavController,

) {

    Scaffold(
        topBar = {
            SavedArticlesToolBar(
                navController = navController
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,



            ) {
                Box(contentAlignment = Alignment.Center){
                    LocalGifImage(resId=R.raw.examplegif, modifier = Modifier.zIndex(2f))
                    Image(
                        painter = painterResource(id = R.drawable.dsadas),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp).zIndex(1f)

                    )

                }




                MarkdownText(
                    fontSize = 16.sp,
                    markdown = mockData,
                    modifier = Modifier.fillMaxSize().padding(25.dp)



                )

            }

        },

    )

}


@Composable
fun SavedArticlesToolBar(
    navController: NavController

) {
    TopAppBar(
        elevation = 0.dp, // Kenarlık kalınlığını sıfıra ayarlar
        backgroundColor = Color.White,
        contentColor = Color.Transparent,
        title = {
            Text(text = "About us")
        },
        navigationIcon = {
            IconButton(onClick = { /* Geri gitme işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.return_back_icon),
                    contentDescription = "Geri",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(32.dp)
                        .clickable {
                            navController.popBackStack()
                        }


                )
            }
        },

        )
}