package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate

@Composable
fun UserProfileScreen(navController: NavController, profileScreenViewModel: UserProfileScreenViewModel = hiltViewModel()) {
    val profileScreenState by profileScreenViewModel.state
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        
        
        if (profileScreenState.isLoading) {
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
                            modifier = Modifier.padding(bottom = 16.dp)
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
                        navController.navigate(Screen.EnneagramTestScreen.route)
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
        } else {
            // No Test Result UI
            NoTestResultUI(navController)
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
fun NoTestResultUI(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Henüz enneagram testini tamamlamadınız.",
            fontSize = 18.sp,
            color = harmonyHavenDarkGreenColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Button(
            onClick = {
                navController.navigate(Screen.EnneagramTestScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Testi Şimdi Al",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun EnneagramResultCard(dominantType: EnneagramScore, wingType: EnneagramScore, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Senin Enneagram Tipin:",
                fontSize = 16.sp,
                color = Color.Gray
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tip ${dominantType.type}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor
                )
                
                if (wingType.type > 0) {
                    Text(
                        text = " (Kanat ${wingType.type})",
                        fontSize = 20.sp,
                        color = harmonyHavenDarkGreenColor
                    )
                }
            }
            
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = harmonyHavenGreen.copy(alpha = 0.3f)
            )
            
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun FamousPersonCard(person: EnneagramFamousPeople) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = person.imageUrl)
                        .build()
                ),
                contentDescription = person.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = person.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor
                )
                
                Text(
                    text = person.desc,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun TypeScoreItem(score: EnneagramScore) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tip ${score.type}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.padding(end = 16.dp)
        )
        
        Box(
            modifier = Modifier
                .weight(1f)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.LightGray)
        ) {
            // Calculate percentage (assuming maximum score is 12 for enneagram)
            val percentage = (score.score.toFloat() / 12).coerceIn(0f, 1f)
            
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage)
                    .clip(RoundedCornerShape(6.dp))
                    .background(harmonyHavenGreen)
            )
        }
        
        Text(
            text = "${score.score}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}