package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.SetSystemBarsAppearance
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.Quote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuoteVerticalList
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuotesContent
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuotesViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel = hiltViewModel()
) {

    val quotes = viewmodel.quotes.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window

    val shouldShowUxDialog1 = viewmodel.shouldShowUxDialog1.collectAsState()

    SideEffect {

        window?.let {

            WindowCompat.setDecorFitsSystemWindows(it, false)

                it.statusBarColor = Color.Transparent.toArgb()
                it.navigationBarColor = Color.Transparent.toArgb()



                val insetsController = WindowCompat.getInsetsController(it, it.decorView)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false

        }

    }






    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if(shouldShowUxDialog1.value){
            UxScrollInformer(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.setShouldShowUxDialog1(false)
                })

        }

        QuoteVerticalList1(quoteList = quotes.value, modifier = Modifier)
        ButtonSurface(modifier = Modifier.align(Alignment.BottomStart), onClick = {
            window?.let {
                WindowCompat.setDecorFitsSystemWindows(it, true)
                it.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }
            navController.navigate(Screen.Main.route){

            }



        })


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteVerticalList1(
    quoteList: List<Quote>, modifier: Modifier
) {

    if (quoteList.isNotEmpty()) {


        val pagerState = rememberPagerState(pageCount = {
            quoteList.size
        })


        Box(modifier = modifier) {
            VerticalPager(
                modifier = Modifier
                    .fillMaxSize()
                    //.background(Color.Cyan)
                    .align(Alignment.Center), state = pagerState
            ) { page ->
                val quote = quoteList[page]
                Quote(quote = quote, modifier = Modifier.zIndex(2f))


            }



        }


    }
}

@Composable
fun ButtonSurface(modifier: Modifier = Modifier,onClick:()->Unit) {
    // Renk kodunu ve alfa değerini ayarlayarak yarı transparan yapıyoruz
    Box(
        modifier = modifier
            .padding(bottom = 60.dp, start = 25.dp)
            .clip(RoundedCornerShape(10.dp))
            .width(50.dp)
            .height(50.dp)
            .background(Color.Gray.copy(alpha = 0.5f)) // Alfa değerini 0.5 olarak ayarlıyoruz
            .clickable { onClick() }
    ) {
       Icon(
           painter = painterResource(id = R.drawable.newspaper),
           contentDescription = null,
           Modifier
               .size(25.dp)
               .align(Alignment.Center)


       )



    }
}