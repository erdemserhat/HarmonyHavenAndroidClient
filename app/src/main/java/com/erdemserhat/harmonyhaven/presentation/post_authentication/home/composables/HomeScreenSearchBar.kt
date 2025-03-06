package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    shouldShowExitButton:Boolean,
    onExitButtonClicked:()->Unit
) {
    val focusManager = LocalFocusManager.current

    val colors = androidx.compose.material3.TextFieldDefaults.colors(
        cursorColor = Color.Red,
        disabledContainerColor = Color.Red,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedLabelColor = Color.Red,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    )
    var isKeyboardOpen by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current




    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(50.dp)
            )
            .fillMaxWidth(0.93f),


    ) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxHeight(0.1f)) {
            Spacer(modifier = Modifier.size(25.dp))
            Image(
                painter = painterResource(id = R.drawable.serch1),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onSearch()
                        keyboardController?.hide()
                    },


                )

        }

        Row(horizontalArrangement = Arrangement.Center) {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Ara...") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch()
                    keyboardController?.hide()


                }),
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(0.9f)
                    .onFocusChanged {
                        onActiveChange(it.isFocused)

                    },
                colors = colors
            )

        }

        if(shouldShowExitButton){
            Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxHeight(0.2f)) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).clickable {
                        focusManager.clearFocus()
                        onExitButtonClicked()
                    },

                )

            }

        }





    }
}