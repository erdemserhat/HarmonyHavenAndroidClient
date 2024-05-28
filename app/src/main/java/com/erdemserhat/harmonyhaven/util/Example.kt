//TODO("LOOK AT HERE") : MODIFY THE PACKAGE NAME

//package com.erdemserhat.harmonyhaven.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * This composable provides the general Screen.
 * use it in navigation graph
 */
@Preview
@Composable
private fun TransactionOperationScreen() {
    TransactionOperationContent()


}

@Preview
@Composable
private fun TransactionOperationContentPreview() {
    TransactionOperationContent()

}


/**
 * This screen provides a raw content for preview and abstracts the current screen
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionOperationContent() {
    //Parent Container
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize(0.5f), contentAlignment = Alignment.Center) {
            // TODO():
            Text(text = "TuranPay Screen Content")
        }


        TransactionHorizontalPager(
            withdrawScreenContent = {
                // TODO(): parameter your withdraw section arguments.
                TransactionTemplateSection(
                    textFieldPlaceHolderText = "Default",
                    amount = "",
                    onAmountValueChanged = {},
                    onButtonClicked = {},
                    buttonTextFieldText = "Default"

                )


            },
            depositScreenContent = {
                TransactionTemplateSection(
                    // TODO(): paremeter your deposit section arguments.
                    textFieldPlaceHolderText = "Default",
                    amount = "",
                    onAmountValueChanged = {},
                    onButtonClicked = {},
                    buttonTextFieldText = "Default"

                )


            }


        )


    }

}

/**
 * A template composable function for transaction sections with a text field and a button.
 *
 * @param textFieldPlaceHolderText Placeholder text for the text field.
 * @param amount The current amount input value.
 * @param onAmountValueChanged A lambda function to handle changes in the text field value.
 * @param onButtonClicked A lambda function to handle button clicks.
 * @param buttonTextFieldText The text displayed on the button.
 */
@Composable
fun TransactionTemplateSection(
    textFieldPlaceHolderText: String = "Default",
    amount: String = "",
    onAmountValueChanged: (String) -> Unit = {},
    onButtonClicked: () -> Unit = {},
    buttonTextFieldText: String = "Default"
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .size(width = 370.dp, height = 60.dp),
            value = amount,
            onValueChange = { onAmountValueChanged(it) },
            label = { Text(text = textFieldPlaceHolderText) },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                focusedLabelColor = Color.Black

            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.size(30.dp))

        Button(
            onClick = onButtonClicked,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )


        ) {
            Text(text = buttonTextFieldText)

        }


    }


}

/**
 * Don't use this composable directly, it's just a reusable template
 */

@Composable
private fun TransactionTitleTemplate(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(24.dp)


    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            //To
            Text(text = "Amount")
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(0.3f)
                .background(color = Color.Black)
        )

    }

}


/**
 * A composable function that manages the horizontal pager for transaction operations.
 *
 * @param withdrawScreenContent The content composable for the withdraw screen.
 * @param depositScreenContent The content composable for the deposit screen.
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionHorizontalPager(
    withdrawScreenContent: @Composable () -> Unit,
    depositScreenContent: @Composable () -> Unit


) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.White)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(2) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) harmonyHavenDarkGreenColor else harmonyHavenGradientWhite
                TransactionTitleTemplate(modifier = Modifier
                    .background(color)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(iteration)
                        }
                    })

            }

        }

        Spacer(modifier = Modifier.size(25.dp))

        HorizontalPager(
            state = pagerState,
            count = 2,
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
        ) { page ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                when (page) {
                    0 -> withdrawScreenContent()
                    1 -> depositScreenContent()
                }

            }

        }

    }


}

