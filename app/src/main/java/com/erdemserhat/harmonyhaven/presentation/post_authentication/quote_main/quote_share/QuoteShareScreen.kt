import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.ImageQuote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.VideoPlayer
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.downloadVideo
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.saveBitmapToFile
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareImage
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToFacebook
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToFacebookStory
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagram
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagram1
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagramStory
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagramStoryTRY
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToWhatsApp
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToX
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareVideo
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalComposeApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun QuoteShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    params: QuoteShareScreenParams = QuoteShareScreenParams(),
    sheetState: ModalBottomSheetState? = null,
    onContentReady: () -> Unit = {}

) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val capturableController = rememberCaptureController()
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    val onSaveImageFile: suspend () -> File? = {
        isLoading = true
        try {
            val bitmapAsync = capturableController.captureAsync()
            val bitmap = bitmapAsync.await()
            val file = saveBitmapToFile(context, bitmap)
            file
        } catch (error: Exception) {
            Log.d("23esdsadsa", error.message.toString())
            null
        } finally {
            isLoading = false
        }
    }


    val onSaveVideoFile: suspend () -> File? = {
        isLoading = true
        try {
            val file = downloadVideo(context, params.quoteUrl)
            file

        } catch (error: Exception) {
            null // Hata durumunda null döner
        } finally {
            isLoading = false

        }
    }


    val isVideo by rememberSaveable {
        mutableStateOf(params.quoteUrl.endsWith(".mp4"))
    }

    LaunchedEffect(Unit) {
        if (isVideo) {
            Log.d("dsadsadsa", "video")

        }

    }



    Column(
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.2f))
            .fillMaxSize()
            .zIndex(3f)
    ) {
        Box(modifier = Modifier.size(60.dp)) {
            Image(
                painter = painterResource(R.drawable.cross),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 25.dp, start = 15.dp)
                    .size(20.dp)
                    .clickable {
                        coroutineScope.launch {
                            navController.popBackStack()
                        }

                    })


        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
        ) {

            if (isVideo) {
                VideoPlayer(
                    videoUrl = params.quoteUrl,
                    isPlaying = true,
                    prepareOnly = true,
                )

            } else {
                ImageQuote(
                    modifier = Modifier,
                    quoteWriter = params.author,
                    shouldAnimate = false,
                    quoteSentence = params.quote,
                    quoteURL = params.quoteUrl,
                    isFirmLogoVisible = true
                )

            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(4f)
                )
            }


        }


        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            ShareItem(painterId = R.drawable.instagram_icon, text = "Instagram") {
                if (!isLoading) {
                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()

                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            Log.d("23esdsadsa", "${file.isFile}")

                            shareToInstagram1(context, file)


                        }
                    }

                }


            }

            ShareItem(
                painterId = R.drawable.instagram_share_story_icon,
                text = "Instagram \n Hikaye"
            ) {
                if (!isLoading) {

                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()
                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            if (isVideo) {
                                shareToInstagram1(context, file)
                            } else {
                                shareToInstagramStory(context, file)
                            }


                        }


                    }
                }

            }

            ShareItem(painterId = R.drawable.facebook_icon, text = "Facebook") {
                if (!isLoading) {
                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()

                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            Log.d("23esdsadsa", "${file.isFile}")

                            shareToFacebook(context, file)


                        }
                    }

                }

            }

            ShareItem(painterId = R.drawable.whatsapp_icon, text = "WhatsApp") {
                if (!isLoading) {
                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()

                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            Log.d("23esdsadsa", "${file.isFile}")

                            shareToWhatsApp(context, file)


                        }
                    }

                }

            }

            ShareItem(painterId = R.drawable.x_icon, text = "X") {
                if (!isLoading) {
                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()

                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            Log.d("23esdsadsa", "${file.isFile}")

                            shareToX(context, file)


                        }
                    }

                }

            }
            ShareItem(painterId = R.drawable.other, text = "Daha Fazla") {
                if (!isLoading) {

                    coroutineScope.launch {
                        val fileAsync = async {
                            if (isVideo) {
                                onSaveVideoFile()
                            } else {
                                onSaveImageFile()
                            }
                        }
                        val file = fileAsync.await()
                        if (file != null) {
                            if (isVideo) {
                                shareVideo(context, file)
                            } else {
                                shareImage(context, file)
                            }


                        }


                    }
                }

            }


        }


    }









    Box(modifier = Modifier.fillMaxSize()) {

    }


    //Original Screen Shot
    ImageQuote(
        modifier = Modifier
            .offset(x = 5000.dp, y = 5000.dp)
            .capturable(capturableController),
        quoteWriter = params.author,
        shouldAnimate = false,
        quoteSentence = params.quote,
        quoteURL = params.quoteUrl,
        isFirmLogoVisible = true
    )


}


@Composable
fun ShareItem(modifier: Modifier = Modifier, painterId: Int, text: String, onClick: () -> Unit) {

    Column(modifier = modifier
        .clickable {
            onClick()
        }
        .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = painterId),
            contentDescription = null

        )
        Text(text, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)

    }


}



