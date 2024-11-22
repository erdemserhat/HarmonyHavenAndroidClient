import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.ImageQuote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.saveBitmapToFile
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareImage
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagram
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToInstagramStory
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToWhatsApp
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.shareToWhatsAppStory
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    val onSaveFile: suspend () -> File? = {
        try {
            val bitmapAsync = capturableController.captureAsync()
            val bitmap = bitmapAsync.await()
            val file = saveBitmapToFile(context, bitmap)
            file
        } catch (error: Exception) {
            Log.d("23esdsadsa", error.message.toString())
            null // Hata durumunda null döner
        }
    }


    Column(
        modifier = Modifier
            .background(Color.Black)
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
            ImageQuote(
                modifier = Modifier,
                quoteWriter = params.author,
                shouldAnimate = false,
                quoteSentence = params.quote,
                quoteURL = params.quoteUrl,
                isFirmLogoVisible = true
            )


        }

        Text("SS", modifier = Modifier.clickable {
            coroutineScope.launch {
                val fileAsyn = async {
                    onSaveFile()
                }
                val file = fileAsyn.await()
                if (file != null) {
                    Log.d("23esdsadsa","${file.isFile}")

                    withContext(Dispatchers.Main) {
                        shareToWhatsAppStory(context, file)

                    }
                }
            }


        }, color = Color.White


        )




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