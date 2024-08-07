package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.util.DefaultAppFont

@Composable
fun AccountInformationRowElement(
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
