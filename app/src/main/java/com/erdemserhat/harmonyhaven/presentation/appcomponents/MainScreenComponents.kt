package com.erdemserhat.harmonyhaven.presentation.appcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenBottomAppBarContainerColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor


////////////////////////////Main Screen Reusable Components ///////////////////////////////////////



////////////////////////////////////////////Component////////////////////////////////////////////////


data class NavigationBarItem(
    val title:String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews:Boolean,
    val badgeCount:Int? = null,
    val route:String

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HarmonyHavenNavigationBar(navController: NavController,selectedItem:MutableIntState) {

    val items = listOf(
        NavigationBarItem("Home", Icons.Filled.Home, Icons.Outlined.Home,true,null,
            Screen.Home.route),
        NavigationBarItem("Notification", Icons.Filled.Notifications, Icons.Outlined.Notifications,false,12,
            Screen.Notification.route),
        NavigationBarItem("Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle,false,null,
            Screen.Profile.route)
    )

    NavigationBar(
        containerColor = harmonyHavenBottomAppBarContainerColor,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 0.dp, height = 50.dp),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = harmonyHavenSelectedNavigationBarItemColor,
                    selectedTextColor = harmonyHavenSelectedNavigationBarItemColor,
                    indicatorColor= harmonyHavenIndicatorColor,
                    unselectedIconColor = harmonyHavenSelectedNavigationBarItemColor

                ),
                onClick = {
                    selectedItem.value = index
                },
                //label = { Text(text = item.title) },
                icon = {

                    BadgedBox(
                        badge = {
                            if(item.badgeCount !=null){
                                Badge {
                                    Text(text = item.badgeCount.toString())

                                }
                            }else if (item.hasNews){
                                Badge()
                            }

                        }
                    ) {
                        if (selectedItem.value==index)
                            Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                        else
                            Icon(imageVector = item.unSelectedIcon, contentDescription = item.title)

                    }

                }






            )


        }
    }
}
////////////////////////////////////////////End of Composable///////////////////////////////////////


////////////////////////////////////////////Component////////////////////////////////////////////////

////////////////////////////////////////////End of Composable///////////////////////////////////////
