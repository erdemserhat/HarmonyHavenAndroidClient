package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.login.LoginViewModel
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenRememberCredentialsCheckbox(
    text: String = stringResource(R.string.remember_me),
    loginViewModel: LoginViewModel) {
    var checkedState by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = harmonyHavenGreen,
            ),
            checked = checkedState,
            onCheckedChange = {
                checkedState = !checkedState

            },
        )
        Text(text = text)

    }
}