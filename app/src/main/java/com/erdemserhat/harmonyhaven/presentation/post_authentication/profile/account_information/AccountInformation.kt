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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information.AccountInformationViewModel
import com.erdemserhat.harmonyhaven.util.DefaultAppFont
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AccountInformationContent(navController: NavController) {
    var shouldShowUpdateNamePopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowUpdatePasswordPopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowSuccessAnimation by rememberSaveable {
        mutableStateOf(false)
    }


    var viewModel:AccountInformationViewModel = hiltViewModel()


    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                title = { Text(text = "Profile") },
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
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {

                Column(
                    modifier =
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {

                    RowElement(
                        modifier = Modifier,
                        title = "Name",
                        titleIcon = R.drawable.profile_svgrepo_com,
                        text = viewModel.userInfo.value.name,
                        shouldShowActionIcon = true,
                        actionIcon = R.drawable.edit_svgrepo_com__1_,
                        actionIconModifier = Modifier.size(45.dp),

                        extraText = "This is not your username or pin. This name will be used in notification and articles to call you.",
                        shouldShowExtraText = true,
                        onRowElementClicked = { shouldShowUpdateNamePopUp = true },

                    )

                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))

                    RowElement(
                        modifier = Modifier,
                        title = "E-Mail",
                        titleIcon = R.drawable.email_svgrepo_com,
                        text = viewModel.userInfo.value.email,
                        shouldShowActionIcon = false,
                        actionIcon = R.drawable.edit_svgrepo_com,
                        extraText = "This is not your username or pin. This name will be used in notification and articles to call you.",
                        shouldShowExtraText = true,
                        onRowElementClicked = {},
                    )
                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))

                    RowElement(
                        modifier = Modifier,
                        title = "Password",
                        titleIcon = R.drawable.password_lock_solid_svgrepo_com,
                        text = "Change your password",
                        shouldShowActionIcon = true,
                        actionIcon = R.drawable.right_arrow_svgrepo_com,
                        actionIconModifier = Modifier.size(36.dp),
                        extraText = "",
                        shouldShowExtraText = false,
                        onRowElementClicked = { shouldShowUpdatePasswordPopUp = true },
                    )
                    RowDividingLine(modifier = Modifier.align(Alignment.CenterHorizontally))


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.editsection),
                            contentDescription = null

                        )

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "For e-mail changes, you can write to us at ",
                                fontFamily = DefaultAppFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF5B5353)
                            )

                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "support@harmonyhaven.com",
                                fontFamily = DefaultAppFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF5FA0FF), // Metnin rengini kırmızı olarak ayarlayın

                            )
                        }
                    }

                }


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
                    detectTapGestures(onTap = {
                        shouldShowUpdatePasswordPopUp = false
                    })
                }, // To dismiss the popup when clicking outside
            contentAlignment = Alignment.BottomCenter

        ) {
            PasswordUpdatePopup(
                onDismissRequest = {
                    shouldShowUpdatePasswordPopUp = false
                }
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

}










@Composable
fun AccountInformationScreen(navController: NavController) {
    AccountInformationContent(navController)
}

@Preview
@Composable
private fun AccountInformationPreview() {
    AccountInformationScreen(rememberNavController())
}

@Composable
fun NameUpdatePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onPositiveButtonClicked: (String) -> Unit = {},
    currentName:String
) {
    val textState = remember {
        mutableStateOf(TextFieldValue(currentName))
    }

    // FocusRequester ve KeyboardController'ı tanımlayın
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // LaunchedEffect içindeki işlemler
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .padding(16.dp)
            .focusable(true)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Enter your name",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = textState.value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    textState.value = text
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            val text = textState.value.text
                            textState.value = textState.value.copy(
                                selection = TextRange(0, text.length)
                            )


                        }
                        keyboardController?.show()  // Klavyeyi açma
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(
                    onClick = { onPositiveButtonClicked(textState.value.text) }
                ) {
                    Text(text = "Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Composable
fun DrawLineExample() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(
            color = Color.Black,
            start = Offset(x = 12f, y = 12f),
            end = Offset(x = 300f, y = 100f),
            strokeWidth = 5f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawLineExamplePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
       // RowElement()
    }

}

@Composable
fun RowElement(
    modifier: Modifier = Modifier,
    title: String = "E-Mail",
    titleIcon: Int = R.drawable.email_svgrepo_com,
    titleModifier: Modifier = Modifier,
    text: String = "me.serhaterdem@gmail.com",
    shouldShowActionIcon: Boolean = true,
    actionIcon: Int = R.drawable.edit_svgrepo_com,
    actionIconModifier: Modifier = Modifier,
    extraText: String = "This is not your username or pin. This name will be used in notification and articles to call you.",
    shouldShowExtraText: Boolean = false,
    onRowElementClicked: () -> Unit = {},

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))

            .clickable { onRowElementClicked() }
    ) {
        Image(
            modifier = Modifier
                .padding(12.dp)
                .size(20.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = titleIcon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFF5B5353))
        )

        Text(
            modifier = Modifier
                .padding(start = 59.dp, top = 0.dp, bottom = 5.dp, end = 5.dp),
            text = title,
            fontFamily = DefaultAppFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF5B5353)
        )

        Text(
            modifier = Modifier
                .padding(start = 59.dp, top = 20.dp, bottom = 5.dp, end = 5.dp),
            text = text,
            fontFamily = DefaultAppFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF342929)
        )

        if (shouldShowExtraText)
            Text(
                modifier = Modifier
                    .padding(
                        start = 59.dp,
                        top = 45.dp,
                        bottom = 5.dp,
                        end = if (shouldShowActionIcon) 44.dp else 5.dp
                    ),
                text = extraText,
                fontFamily = DefaultAppFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF5B5353),
            )


        if (shouldShowActionIcon)
            Image(
                modifier = actionIconModifier
                    .padding(12.dp)
                    .aspectRatio(1f)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = actionIcon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF5FA0FF)),
            )


    }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordUpdatePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val newPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val confirmNewPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val currentPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val isNewPasswordVisible = remember { mutableStateOf(false) }

    val isCurrentPasswordVisible = remember { mutableStateOf(false) }

    val (currentPasswordTfRequester, newPasswordTfRequester, newPasswordConfirmTfRequester) = remember { FocusRequester.createRefs() }


    // FocusRequester ve KeyboardController'ı tanımlayın
    val keyboardController = LocalSoftwareKeyboardController.current

    // LaunchedEffect içindeki işlemler
    LaunchedEffect(Unit) {
        currentPasswordTfRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .padding(16.dp)
            .focusable(true)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Current Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                value = currentPassword.value,
                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    currentPassword.value = text
                },
                visualTransformation = if (isCurrentPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        newPasswordTfRequester.requestFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(currentPasswordTfRequester)
                    .onFocusChanged {
                        keyboardController?.show()  // Klavyeyi açma
                    },

                maxLines = 1,
                trailingIcon = {
                    val image = if (isCurrentPasswordVisible.value) {
                        painterResource(id = R.drawable.visibility_eye_icon)
                    } else {
                        painterResource(id = R.drawable.visibility_off_eye_icon)
                    }

                    // Localized description for accessibility services
                    val description =
                        if (isCurrentPasswordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {
                        isCurrentPasswordVisible.value = !isCurrentPasswordVisible.value
                    }) {
                        Image(painter = image, contentDescription = description)
                    }
                }
            )



            Spacer(modifier = Modifier.size(10.dp))



            Text(
                text = "New Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                value = newPassword.value,
                maxLines = 1,

                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    newPassword.value = text
                },
                visualTransformation = if (isNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        newPasswordConfirmTfRequester.requestFocus() // Optionally hide the keyboard
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(newPasswordTfRequester),
                trailingIcon = {
                    val image = if (isNewPasswordVisible.value) {
                        painterResource(id = R.drawable.visibility_eye_icon)
                    } else {
                        painterResource(id = R.drawable.visibility_off_eye_icon)
                    }

                    // Localized description for accessibility services
                    val description =
                        if (isNewPasswordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {
                        isNewPasswordVisible.value = !isNewPasswordVisible.value
                    }) {
                        Image(painter = image, contentDescription = description)
                    }
                }
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Confirm New Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )
            TextField(
                value = confirmNewPassword.value,
                maxLines = 1,

                visualTransformation = if (isNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    confirmNewPassword.value = text
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(newPasswordConfirmTfRequester)
            )




            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(
                    onClick = { /* TODO: Save action */ }
                ) {
                    Text(text = "Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Composable
fun PasswordInputField() {
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value) {
                Icons.Default.Favorite
            } else {
                Icons.Default.AccountBox
            }

            // Localized description for accessibility services
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = image, description)
            }
        }
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
        modifier = modifier.size(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun Information() {

}

