import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.util.DefaultAppFont

@Composable
fun AccountInformationContent() {
    var shouldShowUpdateNamePopUp by rememberSaveable { mutableStateOf(false) }

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
                                    //navController.popBackStack()
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
                        text = "Serhat",
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
                        text = "me.serhaterdem@gmail.com",
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
                        onRowElementClicked = {},
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
            PopupExample(
                onDismissRequest = {
                    shouldShowUpdateNamePopUp = false
                }
            )
        }


    }


}


@Composable
fun AccountInformationScreen() {
    AccountInformationContent()
}

@Preview
@Composable
private fun AccountInformationPreview() {
    AccountInformationScreen()
}


@Composable
fun UpdateNamePopUp(
    onPositiveButtonClicked: () -> Unit = {},
    onNegativeButtonClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    AlertDialog(
        backgroundColor = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = "Enter Your Name",
                fontFamily = DefaultAppFont
            )
        },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onPositiveButtonClicked()
            }) {
                Text(
                    text = "Save",
                    color = Color.Red,
                    fontFamily = DefaultAppFont
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNegativeButtonClicked()
            }) {
                Text(
                    text = "Cancel",
                    fontFamily = DefaultAppFont
                )
            }
        }
    )
}

@Preview
@Composable
private fun NameEditPopUp() {
    UpdateNamePopUp()
}

@Composable
fun PopupExample(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textState = remember {
        mutableStateOf(TextFieldValue("Serhat ERDEM"))
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
            .background(Color.White)
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
        RowElement()
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
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = actionIcon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF5FA0FF))
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