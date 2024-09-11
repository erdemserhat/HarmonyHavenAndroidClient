package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import LocalGifImageWithFilter
import android.Manifest
import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.FullScreenImage
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel = hiltViewModel()
) {
    val authStatus by viewmodel.authStatus.collectAsState()

    when (authStatus) {
        0 -> UxAuthHelper(modifier = modifier, onClick = { viewmodel.tryLoad() })
        1 -> QuoteMainContent(
            modifier = modifier,
            navController = navController,
            viewmodel = viewmodel
        )

        2 -> UxSessionExp(
            modifier = modifier,
            onClick = { navController.navigate(Screen.Login.route) })


    }


}

@Composable
fun QuoteMainContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel
) {

    val quotes = viewmodel.quotes.collectAsState()


    val shouldShowUxDialog1 = viewmodel.shouldShowUxDialog1.collectAsState()
    val shouldShowUxDialog2 = viewmodel.shouldShowUxDialog2.collectAsState()

    var permissionGranted by remember { mutableStateOf(viewmodel.isPermissionGranted()) }

    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window
    SideEffect {

        window?.let {

            WindowCompat.setDecorFitsSystemWindows(it, false)

            it.statusBarColor = Color.Transparent.toArgb()
            it.navigationBarColor = Color.Transparent.toArgb()


            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
            it.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)

        }


    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            viewmodel.updatePermissionStatus(isGranted)

        }
    )
    LaunchedEffect(Unit) {
        notificationPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }






    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        QuoteVerticalList1(quoteList = quotes.value, modifier = Modifier,viewmodel)
        var isButtonClicked by remember { mutableStateOf(false) }
        if (shouldShowUxDialog1.value) {

            UxScrollInformer(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.setShouldShowUxDialog1(false)
                    isButtonClicked = true
                })

        }
        if(shouldShowUxDialog2.value && isButtonClicked){
            LocalGifImageWithFilter(
                resId = R.drawable.down_animated,
                modifier = Modifier.padding(end = 0.dp, bottom = 130.dp).size(120.dp).align(Alignment.BottomEnd),
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.9f)))


            UxScrollInformer2(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.setShouldShowUxDialog2(false)
                })



        }






        ButtonSurface(modifier = Modifier.align(Alignment.BottomEnd), onClick = {
            window?.let {
                WindowCompat.setDecorFitsSystemWindows(it, true)

            }
            navController.navigate(Screen.Main.route) {

            }


        })


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteVerticalList1(
    quoteList: List<Quote>, modifier: Modifier,viewmodel: QuoteMainViewModel
) {
    if (quoteList.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = {
            quoteList.size
        })

        Box(modifier = modifier) {
            VerticalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                state = pagerState
            ) { page ->
                val quote = quoteList[page]

                // Aktif ve önceki sayfanın görünürlüğünü kontrol edelim
                val isCurrentPageVisible = pagerState.currentPage == page
                val isPreviousPageVisible = pagerState.currentPage == page + 1

                Quote(
                    quote = quote,
                    isVisible = isCurrentPageVisible || isPreviousPageVisible,
                    isCurrentPage = isCurrentPageVisible,
                    modifier = Modifier.zIndex(2f),
                    onDeleteClicked = {viewmodel.deleteQuoteById(quote.id)}


                    )
            }
        }
    }
}

@Composable
fun Quote(
    quote: Quote,
    isVisible: Boolean, // Sayfanın görünür olup olmadığını kontrol eder
    isCurrentPage: Boolean, // Aktif sayfa olup olmadığını kontrol eder
    modifier: Modifier = Modifier,
    onDeleteClicked:()->Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        //Box(modifier = Modifier.align(Alignment.BottomStart).zIndex(2f)){
        //    ButtonSurface1(onClick = onDeleteClicked)
        //}




        if (!quote.imageUrl.endsWith(".mp4")) {
            FullScreenImage(
                quote.imageUrl,
                modifier = Modifier
            )
        } else {
            VideoPlayer(
                videoUrl = quote.imageUrl,
                isPlaying = isCurrentPage, // Sadece aktif sayfa oynatılır
                prepareOnly = isVisible, // Görünür olan ancak aktif olmayan sayfa hazırlanır,
            )
        }

        if (quote.quote != "") {
            Box(
                modifier = modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.8f),
                                Color.Gray.copy(alpha = 0.5f)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )

                    .padding(0.dp) // İçerik için padding
            )
            {
              //  Image(painter = painterResource(id = R.drawable.a1), contentDescription =null,modifier = Modifier.align(
                //    Alignment.TopStart).padding(12.dp), colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.4f)))





                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        color = Color.White.copy(0.9f),
                        text = quote.quote,
                        modifier = Modifier.padding(15.dp),
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 30.sp // Satır yüksekliğini ayarlama
                    )

                    Text(
                        color = Color.White.copy(0.7f),
                        text = quote.writer,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = georgiaFont
                    )

                    Spacer(modifier = Modifier.size(15.dp))
                }
            }


        }


    }
}

@Composable
fun ButtonSurface(modifier: Modifier = Modifier, onClick: () -> Unit) {
    // Renk kodunu ve alfa değerini ayarlayarak yarı transparan yapıyoruz
    Box(
        modifier = modifier
            .padding(bottom = 70.dp, end = 35.dp)
            .clip(RoundedCornerShape(10.dp))
            .width(50.dp)
            .height(50.dp)
            .background(Color.Gray.copy(alpha = 0.2f)) // Alfa değerini 0.5 olarak ayarlıyoruz
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.dashboard),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.5f))


        )


    }
}


@Composable
fun ButtonSurface1(modifier: Modifier = Modifier, onClick: () -> Unit) {
    // Renk kodunu ve alfa değerini ayarlayarak yarı transparan yapıyoruz
    Box(
        modifier = modifier
            .padding(bottom = 70.dp, start = 35.dp)
            .clip(RoundedCornerShape(10.dp))
            .width(50.dp)
            .height(50.dp)
            .background(Color.Gray.copy(alpha = 0.2f)) // Alfa değerini 0.5 olarak ayarlıyoruz
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            Modifier
                .size(30.dp)
                .align(Alignment.Center)


        )


    }
}