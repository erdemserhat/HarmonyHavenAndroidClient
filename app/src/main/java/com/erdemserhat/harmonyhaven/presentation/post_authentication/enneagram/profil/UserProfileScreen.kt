package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramScore
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(navController: NavController, profileScreenViewModel: UserProfileScreenViewModel) {
    val profileScreenState by profileScreenViewModel.state
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldShowIntroTestScreen by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler {
        if (showBottomSheet) {
            showBottomSheet = false
        } else {
            shouldShowIntroTestScreen = false
        }
    }

    LaunchedEffect(Unit) {
        if(profileScreenState.shouldResetScrollState){
            scrollState.scrollTo(0)
            profileScreenViewModel.protectScrollState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                profileScreenViewModel.refreshTestResults(
                    onCompleted = {
                        isRefreshing = false
                    }
                )
            },
            modifier = Modifier.fillMaxSize() // Ensure it takes up the entire screen
        ){
            if(shouldShowIntroTestScreen){
                TestIntroScreen(navController)
            }else if (profileScreenState.isLoading) {
                LoadingUI()
            } else if (profileScreenState.result?.isTestTakenBefore == true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    profileScreenState.result?.detailedResult?.let { result ->
                        // Greeting with username
                        profileScreenState.username?.let { username ->
                            Text(
                                text = "Merhaba, $username",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .clickable {
                                        Log.d("dasdsds",profileScreenState.shouldResetScrollState.toString())
                                    }
                            )
                        }

                        // Main Result Card
                        EnneagramResultCard(result.result.dominantType, result.result.wingType, result.description)

                        Spacer(modifier = Modifier.height(24.dp))

                        // Famous People Section
                        Text(
                            text = "Seninle Aynı Enneagram Tipindeki Ünlüler",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        result.famousPeople.forEach { person ->
                            FamousPersonCard(person)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Chart Section (if available)
                        result.chartUrl.takeIf { it.isNotEmpty() }?.let { chartUrl ->
                            Text(
                                text = "Enneagram Grafik Görünümün",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = chartUrl)
                                            .build()
                                    ),
                                    contentDescription = "Enneagram Chart",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // All Type Scores
                        Text(
                            text = "Tüm Tip Skorların",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        result.result.typeScores.forEach { score ->
                            TypeScoreItem(score)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Exploration Tabs Section
                        ExplorationTabs(navController, result.result.dominantType.type)

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Retake Test Button
                    Button(
                        onClick = {
                            showBottomSheet = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = harmonyHavenGreen
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Testi Tekrar Al",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }else{
                TestIntroScreen(navController)
            }
        }
    }
    
    // Modal Bottom Sheet for test mode selection
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                // Header
                Text(
                    text = "Test Modunu Seçin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
                
                // Divider
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    color = harmonyHavenGreen.copy(alpha = 0.2f)
                )
                
                // Test Mode Cards
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    // Basit Test - Active
                    TestModeSelectionItem(
                        title = "Basit Test",
                        description = "Hızlı sonuç için 36 soru (5-10 dakika)",
                        isEnabled = true,
                        onClick = {
                            showBottomSheet = false
                            navController.navigate(Screen.EnneagramTestScreen.route + "?mode=simple")
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Standart Test - Disabled
                    TestModeSelectionItem(
                        title = "Standart Test",
                        description = "Detaylı analiz için 60 soru (10-15 dakika)",
                        isEnabled = false,
                        comingSoonText = "Yakında",
                        onClick = {}
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Profesyonel Test - Disabled
                    TestModeSelectionItem(
                        title = "Profesyonel Test",
                        description = "Derinlemesine analiz için 108 soru (20-30 dakika)",
                        isEnabled = false,
                        comingSoonText = "Yakında",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ExplorationTabs(navController: NavController, dominantType: Int) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Enneagramı Keşfet", "Kendini Keşfet", "Kariyerini Yönet")
    
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = harmonyHavenGreen,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            color = harmonyHavenGreen,
                            shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                        )
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { 
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        ) 
                    },
                    selectedContentColor = harmonyHavenDarkGreenColor,
                    unselectedContentColor = Color.Gray
                )
            }
        }
        
        when (selectedTabIndex) {
            0 -> EnneagramExploreContent(navController)
            1 -> SelfDiscoveryContent(navController, dominantType)
            2 -> CareerManagementContent(navController, dominantType)
        }
    }
}

@Composable
fun EnneagramExploreContent(navController: NavController) {
    val topicList = listOf(
        "Ennagram nedir",
        "Ana tip kanat tip ve alt tip nedir",
        "Stres hattı ve rahat hattı nedir",
        "Mizaç yapısının kişilik düzeyindeki farklılık seviyeleri",
        "Ennagram hangi alanlarda kullanılıyor",
        "Enneagram tiplerine genel bakış",
        "Enneagram tipimizi bilmek bize ne kazandırır",
        "Enneagram hakkında sıkça sorular sorular"
    )
    
    Column(modifier = Modifier.padding(top = 16.dp)) {
        topicList.forEachIndexed { index, topic ->
            TopicCard(
                title = topic,
                onClick = {
                    // Burayı siz doldurabilirsiniz - örneğin:
                    // navController.navigate("enneagramExplore/${index}")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SelfDiscoveryContent(navController: NavController, dominantType: Int) {
    val topicList = listOf(
        "Kendini İfade etme şeklin",
        "En sık kullanıdıgın cümleler",
        "Ebeveynlik tarzın",
        "Potansiyel risklerin",
        "Temel duygu ve düşünce yönetimi",
        "Davranışsal tepkiler ve stres yönetimi",
        "Motivasyon yönetimini etkileyen faktörler",
        "Problemlere yaklasım tarzın",
        "Stres durumunda tutumların",
        "Rahat durumda tutumların",
        "Hangi kısıtlılıklar görülebilir",
        "Olumlu özelliklerin"
    )
    
    Column(modifier = Modifier.padding(top = 16.dp)) {
        topicList.forEachIndexed { index, topic ->
            TopicCard(
                title = topic,
                onClick = {
                    // Burayı siz doldurabilirsiniz - örneğin:
                    // navController.navigate("selfDiscovery/${dominantType}/${index}")
                },
                typeNumber = dominantType
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CareerManagementContent(navController: NavController, dominantType: Int) {
    val topicList = listOf(
        "Yetenekler ve değerler",
        "Öz saygıyı geliştirme stratejileri",
        "Meslek ve iş hayatı",
        "Zaman yönetimi ve verimlilik teknikleri",
        "Beliriszlikleri yönetme ve stratejiler",
        "İş yapma tarzın",
        "Prensiplerin"
    )
    
    Column(modifier = Modifier.padding(top = 16.dp)) {
        topicList.forEachIndexed { index, topic ->
            TopicCard(
                title = topic,
                onClick = {
                    val article:ArticlePresentableUIModel = ArticlePresentableUIModel(
                        title = "Örnek",
                        content = "örnek"
                    )

                    val bundle = Bundle()
                    bundle.putParcelable("article", article)
                    navController.navigate(
                        route = Screen.Article.route,
                        args = bundle
                    )
                    // Burayı siz doldurabilirsiniz - örneğin:
                    // navController.navigate("careerManagement/${dominantType}/${index}")
                },
                typeNumber = dominantType
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TopicCard(
    title: String,
    onClick: () -> Unit,
    typeNumber: Int? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tik işareti
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Check",
                    tint = harmonyHavenGreen,
                    modifier = Modifier.size(8.dp)
                )
            }
            
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Go to detail",
                tint = harmonyHavenGreen,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        }
    }
}

@Composable
fun LoadingUI() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = harmonyHavenGreen,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Yükleniyor...",
            fontSize = 18.sp,
            color = harmonyHavenDarkGreenColor
        )
    }
}

@Composable
fun TestModeSelectionItem(
    title: String,
    description: String,
    isEnabled: Boolean,
    comingSoonText: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled, onClick = onClick),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isEnabled) harmonyHavenGreen.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.3f))
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isEnabled) harmonyHavenGreen.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Icon",
                        tint = if (isEnabled) harmonyHavenGreen else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Text
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isEnabled) harmonyHavenDarkGreenColor else Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = if (isEnabled) Color.DarkGray else Color.Gray
                    )
                }
                
                if (isEnabled) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Başla",
                        tint = harmonyHavenGreen,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(180f)
                    )
                }
            }
            
            // Coming Soon Badge
            comingSoonText?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = harmonyHavenGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = harmonyHavenDarkGreenColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


