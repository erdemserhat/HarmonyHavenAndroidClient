import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationBottomSection
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationRowElement
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationTopBar
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.NameUpdatePopup
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.PasswordUpdatePopup
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class, ExperimentalLayoutApi::class)
@Composable
fun AccountInformationContent(
    navController: NavController,
    viewModel: AccountInformationViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    val window = (context as Activity).window  // Get the Window instance

    var shouldShowUpdateNamePopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowUpdatePasswordPopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowSuccessAnimation by rememberSaveable {
        mutableStateOf(false)
    }

    val passwordChangeResponse by viewModel.passwordChangeResponseModel

    val nameChangeState by viewModel.nameChangeState



    if (passwordChangeResponse.isSuccessfullyChangedPassword) {
        shouldShowUpdatePasswordPopUp = false
        LaunchedEffect(passwordChangeResponse.isSuccessfullyChangedPassword) {
            shouldShowSuccessAnimation = true
            delay(1000)
            shouldShowSuccessAnimation = false
            viewModel.resetPasswordResetStates()


        }
    }

    if (nameChangeState.result) {
        shouldShowUpdateNamePopUp = false
        LaunchedEffect(nameChangeState.result) {
            shouldShowSuccessAnimation = true
            delay(1000)
            shouldShowSuccessAnimation = false
            viewModel.resetNameState()


        }

    }



    Scaffold(
        modifier = Modifier,
        topBar = {
            AccountInformationTopBar(navController = navController)
        },
        content = { padding ->
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
                .imePadding()
                .navigationBarsPadding()
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
                isError = !nameChangeState.isNameAppropriate,
                onDismissRequest = {
                    shouldShowUpdateNamePopUp = false

                },

                onPositiveButtonClicked = { newName ->
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
                .imePadding()
                .navigationBarsPadding()
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
                onSaveButtonClicked = { newPassword, confirmNewPassword, currentPassword ->
                    viewModel.changePassword(newPassword, currentPassword, confirmNewPassword)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000)
                        withContext(Dispatchers.Main) {
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
fun LocalGifImage(resId: Int, modifier: Modifier = Modifier) {
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
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun LocalGifImageWithFilter(resId: Int, modifier: Modifier = Modifier, colorFilter: ColorFilter) {
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
        contentScale = ContentScale.Crop,
        colorFilter = colorFilter
    )
}
