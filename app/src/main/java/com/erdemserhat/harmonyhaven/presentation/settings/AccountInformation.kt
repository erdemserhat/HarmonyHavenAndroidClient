import android.support.v4.os.IResultReceiver.Default
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
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
                UpdateFieldRow(
                    painterResourceId = R.drawable.profile_svgrepo_com,
                    fieldName = "Name",
                    currentValue = "Serhat Erdem",
                    note = "This is not your username or pin. This name will be used in notifications and articles to call you.",
                    onUpdateFieldRowClicked = { shouldShowUpdateNamePopUp = true }
                )
            }


        }
    )

    if (shouldShowUpdateNamePopUp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { shouldShowUpdateNamePopUp = false }, // To dismiss the popup when clicking outside
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
fun UpdateFieldRow(
    painterResourceId: Int,
    fieldName: String,
    currentValue: String,
    note: String,
    onUpdateFieldRowClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onUpdateFieldRowClicked() },
    ) {
        Image(
            painter = painterResource(id = painterResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(start = 5.dp, top = 20.dp),
            colorFilter = ColorFilter.tint(Color(0xFF5B5353))
        )
        Spacer(modifier = Modifier.size(20.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = fieldName,
                        fontFamily = DefaultAppFont,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF5B5353)
                    )
                    Text(
                        text = currentValue,
                        fontFamily = DefaultAppFont,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF342929)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.edit_svgrepo_com__1_),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(top = 20.dp, end = 20.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF00612D))
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = note,
                fontFamily = DefaultAppFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF5B5353),
                modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
            )
        }
    }
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
    var text by rememberSaveable { mutableStateOf("Serhat Erdem") }

    // FocusRequester ve KeyboardController'ı tanımlayın
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Popup gösterildiğinde TextField'ı odaklamak ve klavyeyi açmak için LaunchedEffect kullanın
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

        Popup(
            alignment = Alignment.BottomCenter,
            onDismissRequest = {
                onDismissRequest()

            },
            properties = PopupProperties(
                focusable = true
            )
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Enter your name",modifier = Modifier.align(Alignment.Start),
                        fontFamily = DefaultAppFont, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(
                        value = text,
                        colors =  TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        ),
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)  // FocusRequester'ı ekleyin
                            .clickable {
                                focusRequester.requestFocus()  // Tıklama olayında odaklanma
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
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                }

        }
    }
}
@Composable
@Preview
fun PreviewPopupExample() {
    PopupExample(onDismissRequest = {})
}
