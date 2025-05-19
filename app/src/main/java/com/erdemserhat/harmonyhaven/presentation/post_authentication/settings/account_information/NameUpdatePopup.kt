package com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont

@Composable
fun NameUpdatePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onPositiveButtonClicked: (String) -> Unit = {},
    currentName: String,
    isError: Boolean = false,
    backgroundColor: Color = Color.White,
    buttonColor: Color = harmonyHavenGreen,
    buttonTextColor: Color = Color.White
) {
    val viewModel: AccountInformationViewModel = hiltViewModel()
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
            .background(backgroundColor, shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .padding(16.dp)
            .focusable(true)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Size nasıl hitap edilmesini istersiniz?",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = ptSansFont,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                isError = isError,
                value = textState.value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    errorIndicatorColor = Color.Red,
                    cursorColor = Color.Black,
                    errorCursorColor = Color.Red,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                onValueChange = { text ->
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
                        keyboardController?.show()
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(
                    onClick = { onPositiveButtonClicked(textState.value.text) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = buttonTextColor,
                        backgroundColor = buttonColor
                    )
                ) {
                    Text(text = "Kaydet", fontFamily = ptSansFont)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = buttonTextColor,
                    backgroundColor = buttonColor
                )
                ) {
                    Text(text = "Vazgeç", fontFamily = ptSansFont)
                }
            }
        }
    }
} 