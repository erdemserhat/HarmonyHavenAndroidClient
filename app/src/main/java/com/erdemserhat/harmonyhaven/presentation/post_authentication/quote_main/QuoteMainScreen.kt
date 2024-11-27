package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.dto.responses.QuoteForOrderModel
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel = hiltViewModel()
) {

    QuoteMainContent(
        modifier = modifier,
        viewmodel = viewmodel,
        navController = navController
    )
    val context = LocalContext.current





}

@Composable
fun QuoteMainContent(
    modifier: Modifier = Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null
) {

    val quotes = viewmodel.quotes.collectAsState()


    val shouldShowUxDialog1 = viewmodel.shouldShowUxDialog1.collectAsState()

    var permissionGranted by remember { mutableStateOf(viewmodel.isPermissionGranted()) }


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
        QuoteVerticalList1(
            quoteList = quotes.value,
            modifier = Modifier,
            viewmodel = viewmodel,
            navController = navController
        )
        var isButtonClicked by remember { mutableStateOf(false) }
        if (shouldShowUxDialog1.value) {

            UxScrollInformer(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.setShouldShowUxDialog1(false)
                    isButtonClicked = true
                })

        }


    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun QuoteVerticalList1(
    quoteList: List<Quote>,
    modifier: Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null,

) {
    val categorySheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val shareQuoteSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var quoteShareScreenParams by rememberSaveable {
        mutableStateOf(QuoteShareScreenParams())
    }



    if (quoteList.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = {
            quoteList.size
        })

        Box(modifier = modifier) {

            val isLikedListEmpty by viewmodel.isLikedListEmpty.collectAsState()
            var categoryPicker by rememberSaveable(stateSaver = categorySelectionSaver) {
                mutableStateOf(CategorySelectionModel())
            }
            LaunchedEffect(Unit) {

            }


            CategoryPickerModalBottomSheet(
                sheetState = categorySheetState,
                onShouldFilterQuotes = { category, shouldShuffle ->
                    viewmodel.filterQuotes(category, shouldShuffle)

                },
                onSaveCategorySelection = {
                    viewmodel.saveCategorySelection(it)

                },
                isLikedListEmpty = isLikedListEmpty,
                onGetCategorySelectionModel = viewmodel.getCategorySelection()


            )



            VerticalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                state = pagerState
            ) { page ->

                // Aktif ve önceki sayfanın görünürlüğünü kontrol edelim
                val isCurrentPageVisible = pagerState.currentPage == page
                val isPreviousPageVisible = pagerState.currentPage == page + 1


                Crossfade(targetState = quoteList[page], label = "") { quote ->

                    Quote(
                        quote = quote,
                        currentScreen = pagerState.currentPage,
                        isVisible = isCurrentPageVisible || isPreviousPageVisible,
                        isCurrentPage = isCurrentPageVisible,
                        modifier = Modifier.zIndex(2f),
                        viewmodel = viewmodel,
                        onShareQuoteClicked = {
                            coroutineScope.launch {
                                shareQuoteSheetState.show()
                            }

                        },
                        onCategoryClicked = {
                            coroutineScope.launch {
                                categorySheetState.show()
                            }
                        },
                        onReachedToLastPage = {
                            if (pagerState.currentPage == pagerState.pageCount - 1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        0,
                                        animationSpec = tween(durationMillis = 1000)
                                    )
                                }
                            }


                        },
                        navController = navController
                    )

                    LaunchedEffect(pagerState.currentPage) {
                        val quoteURL = quoteList[pagerState.currentPage].imageUrl
                        val quoteSentence = quoteList[pagerState.currentPage].quote
                        val writer = quoteList[pagerState.currentPage].writer

                        quoteShareScreenParams = QuoteShareScreenParams(
                            quote = quoteSentence,
                            quoteUrl = quoteURL,
                            author = writer
                        )


                    }
                }




            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            HarmonyHavenProgressIndicator()

        }
    }
}

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalComposeUiApi::class, ExperimentalComposeApi::class
)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Quote(
    quote: Quote,
    currentScreen:Int,
    isVisible: Boolean, // Sayfanın görünür olup olmadığını kontrol eder
    isCurrentPage: Boolean, // Aktif sayfa olup olmadığını kontrol eder
    modifier: Modifier = Modifier,
    viewmodel: QuoteMainViewModel,
    onCategoryClicked: () -> Unit,
    onShareQuoteClicked: () -> Unit,
    onReachedToLastPage: () -> Unit,
    navController: NavController? = null
) {
    var isQuoteLiked by remember { mutableStateOf(quote.isLiked) }
    var isVisibleLikeAnimation by remember { mutableStateOf(false) }
    var shouldAnimateLikeButton by remember { mutableStateOf(false) }
    var categoryPageVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var shouldScreenBeClear by rememberSaveable {
        mutableStateOf(false)
    }
    val capturableController = rememberCaptureController()



    LaunchedEffect(quote) {
        coroutineScope.launch {
            delay(1500)
            onReachedToLastPage()
        }


    }

    LaunchedEffect(quote.isLiked) {
        isQuoteLiked = quote.isLiked
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {

                        isQuoteLiked = true
                        isVisibleLikeAnimation = true
                        viewmodel.likeQuote(quote.id)
                        shouldAnimateLikeButton = true

                    },

                    )


            }
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .zIndex(3f),

            contentAlignment = Alignment.Center
        ) {
            if (isVisibleLikeAnimation)
                LikeAnimation(onAnimationEnd = {
                    isVisibleLikeAnimation = false
                })
        }

        if (!shouldScreenBeClear) {

            Column(
                modifier = Modifier
                    .padding(bottom = 100.dp, end = 2.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(4f),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {


                LikeAnimationWithClickEffect(
                    isQuoteLiked = isQuoteLiked,
                    shouldAnimate = shouldAnimateLikeButton,
                    onLikeClicked = {
                        shouldAnimateLikeButton = true
                        isQuoteLiked = it
                        if (isQuoteLiked) {
                            viewmodel.likeQuote(quote.id)
                        } else {
                            viewmodel.removeLikeQuote(quote.id)
                            viewmodel.removeLikedInternally(quote.id)


                        }

                    },
                    onAnimationEnd = { shouldAnimateLikeButton = false }

                )

                Column(
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .clickable() {
                            onCategoryClicked()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.category),
                        contentDescription = null

                    )
                    Text("Kategori", color = Color.White, fontSize = 10.sp)

                }









                Column(
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .clickable {
                            coroutineScope.launch {
                                launch(Dispatchers.IO) {
                                    onShareQuoteClicked()
                                    val bundle = Bundle()
                                    bundle.putParcelable("params", QuoteShareScreenParams(
                                        quoteUrl = quote.imageUrl,
                                        quote = quote.quote,
                                        author = quote.writer

                                    ))

                                    viewmodel.updateSelectedQuote(QuoteForOrderModel(
                                        id = quote.id,
                                        quote = quote.quote,
                                        writer = quote.writer,
                                        imageUrl = quote.imageUrl,
                                        currentPage = currentScreen
                                    )


                                    )

                                    withContext(Dispatchers.Main){
                                        navController!!.navigate(
                                            route = Screen.QuoteShareScreen.route,
                                            args = bundle


                                        )
                                    }

                                }
                            }


                        },
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(R.drawable.share__3_),
                        contentDescription = null

                    )
                    Text("Paylaş", color = Color.White, fontSize = 10.sp)

                }


            }

        }










        if (!quote.imageUrl.endsWith(".mp4")) {

            ImageQuote(
                modifier = Modifier.align(Alignment.Center),
                quoteWriter = quote.writer,
                quoteSentence = quote.quote,
                quoteURL = quote.imageUrl


            )


        } else {
            VideoPlayer(
                videoUrl = quote.imageUrl,
                isPlaying = isCurrentPage, // Sadece aktif sayfa oynatılır
                prepareOnly = isVisible, // Görünür olan ancak aktif olmayan sayfa hazırlanır,
            )

            if (quote.quote != "") {
                ImageQuote(
                    modifier = Modifier.align(Alignment.Center),
                    quoteWriter = quote.writer,
                    quoteSentence = quote.quote,
                    quoteURL = quote.imageUrl,


                    )
            }
        }


    }
}


@Composable
fun LikeAnimation(onAnimationEnd: () -> Unit) {
    val scale = remember { Animatable(0f) } // Başlangıç ölçeği
    val alpha = remember { Animatable(0f) } // Başlangıç opaklık

    // Composable her çağrıldığında animasyonu otomatik başlatmak için
    LaunchedEffect(Unit) {
        scale.snapTo(0f)
        alpha.snapTo(1f)

        // İlk büyütme animasyonu
        scale.animateTo(
            targetValue = 1.3f,
            animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
        )

        // Küçültme animasyonu
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )

        // Opaklık azaltma animasyonu ve ardından onAnimationEnd çağırma
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(200)
        )

        // Animasyon tamamlandığında işlemi çağırın
        onAnimationEnd()
    }

    Box(
        modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.likedwhitefilled),
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .size(72.dp)

        )
    }
}

@Composable
fun LikeAnimationWithClickEffect(
    isQuoteLiked: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    shouldAnimate: Boolean = false,
    onAnimationEnd: () -> Unit = {}
) {
    // Animasyon için animatable state
    val scale = remember { Animatable(1f) } // Başlangıç ölçeği 1 (normal boyut)
    val coroutineScope = rememberCoroutineScope()

    // Tıklama efektini başlat
    LaunchedEffect(isQuoteLiked) {

        coroutineScope.launch {
            if (shouldAnimate) {
                // Tıklandığında büyüme animasyonu
                scale.snapTo(1f) // Başlangıçta normal boyut
                scale.animateTo(
                    targetValue = 1.2f, // 1.2 katına büyüme
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
                // Sonra eski boyutuna dönme
                scale.animateTo(
                    targetValue = 1f, // Eski boyutuna geri dön
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )

                onAnimationEnd()

            }

        }
    }

    // Column içinde ikon ve metin
    Column(
        modifier = Modifier.padding(bottom = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(28.dp)
                .scale(scale.value)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, // Etkileşim kaynağını özelleştiriyoruz
                    indication = null

                ) {


                    onLikeClicked(!isQuoteLiked)
                },
            painter = if (isQuoteLiked) painterResource(R.drawable.likedredfilled) else painterResource(
                R.drawable.likedwhiteunfilled
            ),
            contentDescription = null
        )
        Text("Beğen...", color = Color.White, fontSize = 10.sp)
    }
}

@Preview
@Composable
private fun sfsdf() {
    // Box(modifier = Modifier
    //      .fillMaxSize()
    //     .background(Color.Red)) {
    //     BottomSheetExample()
    // }


}


@Composable
fun ShakingComponent(content: @Composable (Dp) -> Unit) {
    // Titreme efektini kontrol etmek için bir animasyon durumu oluşturuyoruz
    val shakeAnim = remember { Animatable(0f) }

    // Titreme animasyonunu başlatmak için bir LaunchedEffect bloğu kullanıyoruz
    LaunchedEffect(Unit) {
        repeat(5) { // Titreme hareketini 5 kez tekrarlayacak
            shakeAnim.animateTo(
                targetValue = 1f, //
                animationSpec = tween(durationMillis = 100)
            )
            shakeAnim.animateTo(
                targetValue = -1f,
                animationSpec = tween(durationMillis = 100)
            )
        }



        shakeAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 100)
        )
    }

    content(shakeAnim.value.dp)


}


