package com.erdemserhat.harmonyhaven.presentation.navigation.navbar.navbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    pageIndex: Int,
    onNavigationBarItemClicked:(index:Int)->Unit

) {

    Column(
        modifier = Modifier
            .background(
                color = if (pageIndex == 1) Color.Black
                else Color.White.copy(
                    alpha = 0.05f
                )
            )
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(64.dp),  // Increased height to accommodate indicator
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationBarItems.items.forEachIndexed { index, item ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            role = Role.Tab,
                            onClick = {
                              onNavigationBarItemClicked(index)
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val iconResource = if (pageIndex == 1) {
                        if (pageIndex == index) item.selectedIconDarkIcon else item.unSelectedIconDarkIcon
                    } else {
                        if (pageIndex == index) item.selectedIconWhiteIcon else item.unSelectedIconWhiteIcon
                    }

                    val isSelected = pageIndex == index
                    val textColor = if (pageIndex == 1) {
                        if (isSelected) Color.White else Color.White.copy(alpha = 0.6f)
                    } else {
                        if (isSelected) Color.Black else Color.Black.copy(alpha = 0.6f)
                    }

                    // Indicator color animation
                    val indicatorColor = animateColorAsState(
                        targetValue = if (isSelected) {
                            if (pageIndex == 1) Color.White else Color.Black
                        } else Color.Transparent,
                        label = "indicatorColor"
                    )

                    // Indicator width animation
                    val indicatorWidth = animateDpAsState(
                        targetValue = if (isSelected) 24.dp else 0.dp,
                        label = "indicatorWidth"
                    )

                    Image(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = iconResource),
                        contentDescription = item.title
                    )

                    Text(
                        text = item.title,
                        color = textColor,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    // Indicator
                    Box(
                        modifier = Modifier
                            .padding(top = 3.dp)
                            .width(indicatorWidth.value)
                            .height(2.dp)
                            .background(
                                color = indicatorColor.value,
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding().value.dp
                )
                .background(Color.Transparent),
        )
    }


}