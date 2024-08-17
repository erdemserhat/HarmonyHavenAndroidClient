import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.AccountInformationBottomSection
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.AccountInformationRowElement
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.AccountInformationTopBar
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.AccountInformationViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.NameUpdatePopup
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.PasswordUpdatePopup
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AccountInformationContent(
    navController: NavController,
    viewModel: AccountInformationViewModel = hiltViewModel()

) {
    var shouldShowUpdateNamePopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowUpdatePasswordPopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowSuccessAnimation by rememberSaveable {
        mutableStateOf(false)
    }



    val passwordChangeResponse by viewModel.passwordChangeResponseModel

    if(passwordChangeResponse.isSuccessfullyChangedPassword) {
        shouldShowUpdatePasswordPopUp = false
        LaunchedEffect(passwordChangeResponse.isSuccessfullyChangedPassword) {
            shouldShowSuccessAnimation = true
            delay(1000)
            shouldShowSuccessAnimation = false
            viewModel.resetPasswordResetStates()


        }
    }



    Scaffold(
        topBar = {
            AccountInformationTopBar(navController = navController)
        },
        content = {padding->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(padding)
                    .fillMaxSize()
            ) {



                    AccountInformationRowElement(
                        modifier = Modifier,
                        title = "Ad",
                        titleIcon = R.drawable.profile_svgrepo_com,
                        text = viewModel.userInfo.value.name,
                        shouldShowActionIcon = true,
                        actionIcon = R.drawable.edit_svgrepo_com__1_,
                        actionIconModifier = Modifier.size(45.dp),

                        extraText = "Bu, kullanıcı adınız değildir. Bu isim, bildirimlerde size hitap etmek için kullanılacaktır.",
                        shouldShowExtraText = true,
                        onRowElementClicked = { shouldShowUpdateNamePopUp = true },

                    )

                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))

                    AccountInformationRowElement(
                        modifier = Modifier,
                        title = "E-Posta",
                        titleIcon = R.drawable.email_svgrepo_com,
                        text = viewModel.userInfo.value.email,
                        shouldShowActionIcon = false,
                        actionIcon = R.drawable.edit_svgrepo_com,
                        extraText = "Güvenlik nedenlerinden dolayı e-posta adresinizi doğrudan güncellemiyoruz. Talebinizi bize e-posta göndererek iletebilirsiniz.",
                        shouldShowExtraText = true,
                        onRowElementClicked = {},
                    )
                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))

                    AccountInformationRowElement(
                        modifier = Modifier,
                        title = "Şifre",
                        titleIcon = R.drawable.password_lock_solid_svgrepo_com,
                        text = "Şifreni Güncelle",
                        shouldShowActionIcon = true,
                        actionIcon = R.drawable.right_arrow_svgrepo_com,
                        actionIconModifier = Modifier.size(36.dp),
                        extraText = "",
                        shouldShowExtraText = false,
                        onRowElementClicked = { shouldShowUpdatePasswordPopUp = true },
                    )
                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))

                    AccountInformationBottomSection()



                }





        }
    )


    if (shouldShowUpdateNamePopUp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        shouldShowUpdateNamePopUp = false
                    })
                }, // To dismiss the popup when clicking outside
            contentAlignment = Alignment.BottomCenter

        ) {
            NameUpdatePopup(
                onDismissRequest = {
                    shouldShowUpdateNamePopUp = false

                },

                onPositiveButtonClicked = {newName->
                    shouldShowSuccessAnimation = true
                    shouldShowUpdateNamePopUp = false
                    viewModel.changeName(newName)
                },
                currentName = viewModel.userInfo.value.name,

            )
        }


    }

    if (shouldShowUpdatePasswordPopUp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        shouldShowUpdatePasswordPopUp = false
                        viewModel.resetPasswordResetStates()
                    })
                }, // To dismiss the popup when clicking outside
            contentAlignment = Alignment.BottomCenter

        ) {
            PasswordUpdatePopup(
                onDismissRequest = {
                    shouldShowUpdatePasswordPopUp = false
                    viewModel.resetPasswordResetStates()
                },
                onSaveButtonClicked = {
                    newPassword, confirmNewPassword, currentPassword ->
                    viewModel.changePassword(newPassword,currentPassword,confirmNewPassword)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000)
                        withContext(Dispatchers.Main){
                            viewModel.resetPasswordResetStates()
                        }

                    }



                },
                isNewPasswordAppropriate = viewModel.passwordChangeResponseModel.value.isNewPasswordAppropriate,
                isCurrentPasswordCorrect = viewModel.passwordChangeResponseModel.value.isCurrentPasswordCorrect,
                isNewPasswordsMatch = viewModel.passwordChangeResponseModel.value.isNewPasswordsMatch,
                isLoading = passwordChangeResponse.isLoading,
                isCurrentPasswordShort = passwordChangeResponse.isCurrentPasswordShort

            )
        }


    }

    if (shouldShowSuccessAnimation) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocalGifImage(resId = R.raw.successfullygif)
            LaunchedEffect(Unit) {
                delay(1000)
                shouldShowSuccessAnimation = false
            }
        }
    }

    if (passwordChangeResponse.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocalGifImage(resId = R.raw.loading)

        }
    }

}

@Composable
fun AccountInformationScreen(navController: NavController) {
    AccountInformationContent(navController)
}





@Composable
fun RowDividingLine(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .border(1.dp, Color(0xFFEDEDED))
            .height(1.dp)

    )

}



@Composable
fun LocalGifImage(resId: Int = R.raw.examplegif, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(resId)
            .decoderFactory(GifDecoder.Factory())
            .scale(Scale.FIT)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

/**
 * TODO():
 */