package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R

@Composable
fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {}
) {
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



    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth(0.9f)
        ,
        horizontalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.size(25.dp))
        Image(
            painter = painterResource(id = R.drawable.serch1),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(24.dp),


            )
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Ara...") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .onFocusChanged {
                    onActiveChange(it.isFocused)

                },
            colors = colors
        )


    }
}