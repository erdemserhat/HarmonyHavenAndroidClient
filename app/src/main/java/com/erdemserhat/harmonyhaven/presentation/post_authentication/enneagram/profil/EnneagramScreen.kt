package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import androidx.compose.ui.draw.rotate
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople
import androidx.compose.ui.platform.LocalConfiguration
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnneagramScreen(navController: NavController, profileScreenViewModel: UserProfileScreenViewModel) {
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
    
    // For debugging
    LaunchedEffect(profileScreenState.result?.detailedResult?.result?.wingType) {
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
            .background(Color(0xFFF8F8F8)),
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
            modifier = Modifier.fillMaxSize()
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
                ) {
                    // Get the detailed result for easier access
                    val detailedResult = profileScreenState.result?.detailedResult

                    // Comprehensive logging of the data structure
                    Log.d("EnneagramScreen", "Complete Result: ${profileScreenState.result}")
                    Log.d("EnneagramScreen", "Detailed Result: $detailedResult")
                    Log.d("EnneagramScreen", "Test Result: ${detailedResult?.result}")
                    Log.d("EnneagramScreen", "All Type Scores: ${detailedResult?.result?.typeScores}")

                    // Extract type information with direct access to the EnneagramScore objects
                    val dominantTypeScore = detailedResult?.result?.dominantType
                    val wingTypeScore = detailedResult?.result?.wingType

                    Log.d("EnneagramScreen", "Dominant Type Score: $dominantTypeScore")
                    Log.d("EnneagramScreen", "Wing Type Score: $wingTypeScore")

                    // Extract type and score values
                    val dominantType = dominantTypeScore?.type ?: 0
                    val dominantScore = dominantTypeScore?.score ?: 0
                    val wingType = wingTypeScore?.enneagramBasedWingType ?: 0
                    val pointBasedWingType = wingTypeScore?.pointBasedWingType ?: 0

                    Log.d("EnneagramScreen", "Dominant Type: $dominantType, Score: $dominantScore")
                    Log.d("EnneagramScreen", "Wing Type: $wingType, Score: $pointBasedWingType")

                    // Intro Card - passing pointBasedWingType as the fourth parameter
                    EnneagramIntroCard(
                        userName = profileScreenState.username ?: "",
                        dominantType = dominantType,
                        wingType = wingType,
                        wingTypePoint = pointBasedWingType,
                        profileImageUrl = detailedResult?.chartUrl?.personalityImageUrl ?: ""
                    )

                    // Main Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        profileScreenState.result?.detailedResult?.let { result ->

                            // MOVED HERE: New Section - Relationship Compatibility
                            /*
                            Text(
                                text = "Diğer Tiplerle İlişki Durumu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                fontFamily = ptSansFont,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Compatibility Matching Card
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                ) {
                                    Text(
                                        text = "Eşleşme Kodunu Girerek Uyumu Göster",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = harmonyHavenDarkGreenColor,
                                        fontFamily = ptSansFont,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                    // Code input section
                                    var matchCode by remember { mutableStateOf("") }
                                    var showCompatibilitySheet by remember { mutableStateOf(false) }
                                    val compatibilitySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Text field for entering code
                                        OutlinedTextField(
                                            value = matchCode,
                                            onValueChange = { matchCode = it },
                                            placeholder = { Text("Eşleşme kodunu gir...") },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp),
                                            singleLine = true
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // Button to submit code
                                        Button(
                                            onClick = {
                                                if (matchCode.isNotEmpty()) {
                                                    showCompatibilitySheet = true
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = harmonyHavenGreen
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = "Karşılaştır",
                                                color = Color.White,
                                                fontFamily = ptSansFont
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Your matching code to share
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = harmonyHavenGreen.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text(
                                                    text = "Senin Eşleşme Kodun:",
                                                    fontSize = 14.sp,
                                                    color = Color.DarkGray,
                                                    fontFamily = ptSansFont
                                                )

                                                Text(
                                                    text = "ENNE-${dominantType}${wingType}-${profileScreenState.username?.take(3)?.uppercase() ?: "XXX"}",
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = harmonyHavenDarkGreenColor,
                                                    fontFamily = ptSansFont
                                                )
                                            }

                                            Button(
                                                onClick = { /* Copy to clipboard functionality */ },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = harmonyHavenGreen.copy(alpha = 0.2f),
                                                    contentColor = harmonyHavenDarkGreenColor
                                                ),
                                                shape = RoundedCornerShape(8.dp),
                                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = "Kopyala",
                                                    fontSize = 14.sp,
                                                    fontFamily = ptSansFont
                                                )
                                            }
                                        }
                                    }

                                    // Bottom Sheet for Compatibility Results - now inside the Column
                                    if (showCompatibilitySheet) {
                                        ModalBottomSheet(
                                            onDismissRequest = { showCompatibilitySheet = false },
                                            sheetState = compatibilitySheetState,
                                            containerColor = Color.White,
                                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(20.dp)
                                                    .padding(bottom = 40.dp)
                                            ) {
                                                // Header
                                                Text(
                                                    text = "Uyum Analizi",
                                                    fontSize = 22.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = harmonyHavenDarkGreenColor,
                                                    fontFamily = ptSansFont,
                                                    modifier = Modifier.padding(bottom = 20.dp)
                                                )

                                                // Match info
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    // Your type
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        modifier = Modifier.weight(1f)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(60.dp)
                                                                .background(
                                                                    color = harmonyHavenGreen,
                                                                    shape = CircleShape
                                                                ),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                text = "$dominantType",
                                                                fontSize = 24.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.White,
                                                                fontFamily = ptSansFont
                                                            )
                                                        }

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text(
                                                            text = "Sen",
                                                            fontSize = 14.sp,
                                                            color = Color.DarkGray,
                                                            fontFamily = ptSansFont
                                                        )
                                                    }

                                                    // Compatibility indicator
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(horizontal = 8.dp),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Column(
                                                            horizontalAlignment = Alignment.CenterHorizontally
                                                        ) {
                                                            Text(
                                                                text = "%76",
                                                                fontSize = 32.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = harmonyHavenDarkGreenColor,
                                                                fontFamily = ptSansFont
                                                            )

                                                            Text(
                                                                text = "Uyum",
                                                                fontSize = 14.sp,
                                                                color = Color.DarkGray,
                                                                fontFamily = ptSansFont
                                                            )
                                                        }
                                                    }

                                                    // Their type
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        modifier = Modifier.weight(1f)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(60.dp)
                                                                .background(
                                                                    color = Color(0xFF6C8EBF),
                                                                    shape = CircleShape
                                                                ),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                text = "4",
                                                                fontSize = 24.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.White,
                                                                fontFamily = ptSansFont
                                                            )
                                                        }

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text(
                                                            text = "Ayşe",
                                                            fontSize = 14.sp,
                                                            color = Color.DarkGray,
                                                            fontFamily = ptSansFont
                                                        )
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(24.dp))

                                                HorizontalDivider(
                                                    modifier = Modifier.padding(vertical = 8.dp),
                                                    color = harmonyHavenGreen.copy(alpha = 0.2f),
                                                    thickness = 1.dp
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                // Compatibility details
                                                CompatibilityDetail(
                                                    title = "İş Birliği",
                                                    percentage = 85,
                                                    description = "İş arkadaşlığı için çok uyumlu bir kombinasyon oluşturuyorsunuz."
                                                )

                                                CompatibilityDetail(
                                                    title = "İlişki",
                                                    percentage = 72,
                                                    description = "Yakın ilişkilerde bazı zorluklar yaşanabilir ancak genellikle birbirinizi tamamlıyorsunuz."
                                                )

                                                CompatibilityDetail(
                                                    title = "İletişim",
                                                    percentage = 68,
                                                    description = "İletişim stilleriniz farklı olsa da anlayış gösterirseniz etkili iletişim kurabilirsiniz."
                                                )

                                                CompatibilityDetail(
                                                    title = "Günlük Yaşam",
                                                    percentage = 79,
                                                    description = "Günlük rutinlerde ve alışkanlıklarda birbirinize destek olabilirsiniz."
                                                )

                                                Spacer(modifier = Modifier.height(16.dp))

                                                // Close button
                                                Button(
                                                    onClick = { showCompatibilitySheet = false },
                                                    modifier = Modifier.fillMaxWidth(),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = harmonyHavenGreen
                                                    ),
                                                    shape = RoundedCornerShape(12.dp)
                                                ) {
                                                    Text(
                                                        text = "Kapat",
                                                        color = Color.White,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        fontFamily = ptSansFont
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            */

                            // Main Result Card - Removed repeated type number and image
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                ) {
                                    // Removed "Senin Enneagram Tipin:" text and type display
                                    // Removed the AsyncImage that showed the type again

                                    // Function to get type name based on type number
                                    fun getTypeName(typeNumber: Int): String {
                                        return when (typeNumber) {
                                            1 -> "Kusursuzluğu Arayan / Reformcu"
                                            2 -> "Seven / Sevilen / Yardım Sever"
                                            3 -> "Başaran / İddialı"
                                            4 -> "Bireyci / Özgün / Romantik"
                                            5 -> "Araştırmacı / Gözlemci"
                                            6 -> "Sadık / Sorgulayıcı"
                                            7 -> "Maceracı / İyimser / Coşkulu"
                                            8 -> "Meydan Okuyan / Lider"
                                            9 -> "Barışçı / Arabulucu"
                                            else -> ""
                                        }
                                    }

                                    // Display type name at the top
                                    Text(
                                        text = "Tip ${result.result.dominantType.type}: ${getTypeName(result.result.dominantType.type)}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = harmonyHavenDarkGreenColor,
                                        fontFamily = ptSansFont,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = harmonyHavenGreen.copy(alpha = 0.2f),
                                        thickness = 1.dp
                                    )

                                    val customStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color.DarkGray,
                                        lineHeight = 25.sp,
                                        fontFamily = ptSansFont
                                    )

                                    MarkdownText(
                                        maxLines = Int.MAX_VALUE,
                                        syntaxHighlightColor = Color.Black.copy(0.18f),
                                        style = customStyle,
                                        markdown = result.description,
                                        isTextSelectable = true,
                                    )

                                    profileScreenState.article?.let {article->
                                        Spacer(modifier = Modifier.size(25.dp))
                                        Button(
                                            onClick = {
                                                    val bundle = Bundle()
                                                    bundle.putParcelable("article", article)
                                                    navController.navigate(
                                                        route = Screen.Article.route,
                                                        args = bundle
                                                    )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = harmonyHavenGreen
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = "Detaylı Açıklamaya Git",
                                                color = Color.White,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium,
                                                fontFamily = ptSansFont
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Famous People Section
                            Text(
                                text = "Seninle Aynı Enneagram Tipindeki Ünlüler",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                fontFamily = ptSansFont,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                                    .padding(bottom = 8.dp, start = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp)
                            ) {
                                items(result.famousPeople) { person ->
                                    FamousPersonCard(person = person)
                                }
                            }

                            // See All Button
                            Button(
                                onClick = {
                                    // Create a bundle with the famous people list
                                    val bundle = Bundle()
                                    // Convert the list to ArrayList
                                    val famousPeopleArrayList = ArrayList(result.famousPeople)
                                    bundle.putParcelableArrayList("famousPeople", famousPeopleArrayList)
                                    navController.navigate(
                                        route = Screen.FamousPeopleScreen.route,
                                        args = bundle
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = harmonyHavenGreen
                                ),
                                shape = RoundedCornerShape(12.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 0.dp
                                ),
                                border = BorderStroke(1.dp, harmonyHavenGreen.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = "Tümünü Görüntüle",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = ptSansFont
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back),
                                    contentDescription = "Tümünü Görüntüle",
                                    modifier = Modifier
                                        .size(18.dp)
                                        .rotate(180f)
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // All Type Scores
                            Text(
                                text = "Tüm Tip Skorların",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                fontFamily = ptSansFont,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    // Function to get type name based on type number
                                    fun getTypeName(typeNumber: Int): String {
                                        return when (typeNumber) {
                                            1 -> "Kusursuzluğu Arayan / Reformcu"
                                            2 -> "Seven / Sevilen / Yardım Sever"
                                            3 -> "Başaran / İddialı"
                                            4 -> "Bireyci / Özgün / Romantik"
                                            5 -> "Araştırmacı / Gözlemci"
                                            6 -> "Sadık / Sorgulayıcı"
                                            7 -> "Maceracı / İyimser / Coşkulu"
                                            8 -> "Meydan Okuyan / Lider"
                                            9 -> "Barışçı / Arabulucu"
                                            else -> ""
                                        }
                                    }

                                    result.result.typeScores.forEach { score ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(CircleShape)
                                                    .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${score.type}",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = harmonyHavenGreen,
                                                    fontFamily = ptSansFont
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(12.dp))

                                            // Type name
                                            Column(
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = getTypeName(score.type),
                                                    fontSize = 14.sp,
                                                    color = Color.DarkGray,
                                                    fontFamily = ptSansFont,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                // Progress bar
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(12.dp)
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(Color(0xFFEEEEEE))
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
                                            }

                                            Text(
                                                text = "${score.score}",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = harmonyHavenDarkGreenColor,
                                                fontFamily = ptSansFont,
                                                modifier = Modifier.padding(start = 16.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(40.dp))
                        }

                        // Retake Test Button
                        Button(
                            onClick = { showBottomSheet = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = harmonyHavenGreen
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Testi Tekrar Al",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = ptSansFont
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
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
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            ) {
                // Header with icon
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = harmonyHavenGreen.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.enneagram_black),
                            contentDescription = "Enneagram Icon",
                            tint = harmonyHavenGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                
                // Title
                Text(
                    text = "Test Modunu Seçin",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor,
                    fontFamily = ptSansFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
                
                // Subtitle
                Text(
                    text = "Test türünü seçerek kişilik analizinizi başlatın",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    fontFamily = ptSansFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
                
                // Divider
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    color = harmonyHavenGreen.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
                
                // Test Mode Cards
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
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
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Cancel button
                    Button(
                        onClick = { showBottomSheet = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray.copy(alpha = 0.3f),
                            contentColor = Color.DarkGray
                        ),
                        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "Vazgeç",
                            fontSize = 16.sp,
                            fontFamily = ptSansFont,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ExplorationTabs(navController: NavController, dominantType: Int) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Enneagramı Keşfet")

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
                            fontSize = 16.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = ptSansFont
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        val bundle = Bundle()
                        bundle.putParcelable("article", ArticlePresentableUIModel())
                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundle
                        )
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
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
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Check",
                            tint = harmonyHavenGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = topic,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontFamily = ptSansFont,
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        // navController.navigate("selfDiscovery/${dominantType}/${index}")
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
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
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$dominantType",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenGreen,
                            fontFamily = ptSansFont
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = topic,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontFamily = ptSansFont,
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
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
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
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
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$dominantType",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenGreen,
                            fontFamily = ptSansFont
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = topic,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontFamily = ptSansFont,
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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = harmonyHavenGreen,
            modifier = Modifier.size(60.dp),
            strokeWidth = 5.dp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Yükleniyor...",
            fontSize = 20.sp,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont,
            fontWeight = FontWeight.Medium
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, if (isEnabled) harmonyHavenGreen.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f))
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon with gradient background for enabled items
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background( brush =
                            if (isEnabled) 
                                Brush.linearGradient(
                                    colors = listOf(
                                        harmonyHavenGreen.copy(alpha = 0.15f),
                                        harmonyHavenGreen.copy(alpha = 0.05f)
                                    )
                                )
                            else
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color.Gray.copy(alpha = 0.1f),
                                        Color.Gray.copy(alpha = 0.1f)
                                    )
                                )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Use a dot icon for all test types
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = if (isEnabled) harmonyHavenGreen else Color.Gray,
                                shape = CircleShape
                            )
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
                        color = if (isEnabled) harmonyHavenDarkGreenColor else Color.Gray,
                        fontFamily = ptSansFont
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = if (isEnabled) Color.DarkGray else Color.Gray,
                        fontFamily = ptSansFont,
                        lineHeight = 20.sp
                    )
                }
                
                if (isEnabled) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = harmonyHavenGreen.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Başla",
                            tint = harmonyHavenGreen,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f)
                        )
                    }
                }
            }
            
            // Coming Soon Badge
            comingSoonText?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            color = harmonyHavenGreen.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = harmonyHavenDarkGreenColor,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ptSansFont
                    )
                }
            }
        }
    }
}

@Composable
fun FamousPersonCard(person: EnneagramFamousPeople) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(190.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = person.imageUrl)
                        .build()
                ),
                contentDescription = person.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = person.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenDarkGreenColor,
                fontFamily = ptSansFont,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = person.desc,
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontFamily = ptSansFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EnneagramIntroCard(
    userName: String,
    dominantType: Int,
    wingType: Int,
    wingTypePoint: Int = 0,
    profileImageUrl: String
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val introCardHeight = screenHeight / 3.5f  // Taller to fit the tab layout
    
    // Check if Alt Tip is the same as Kanat Mizaç
    val showAltTip = wingType != wingTypePoint && wingTypePoint > 0
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header with name and test type
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Name
                    Text(
                        text = "$userName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = ptSansFont
                    )
                    
                    // Test type
                    Text(
                        text = "için Mizaç Testi",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontFamily = ptSansFont
                    )
                }
            }
            
            // Tab layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                // Ana Mizaç Tab (active)
                Box(
                    modifier = Modifier
                        .weight(if (showAltTip) 1f else 1.5f)
                        .fillMaxHeight()
                        .background(harmonyHavenGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ana Mizaç:",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            fontFamily = ptSansFont
                        )
                        
                        Text(
                            text = "$dominantType",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = ptSansFont
                        )
                    }
                }
                
                // Kanat Mizaç Tab
                Box(
                    modifier = Modifier
                        .weight(if (showAltTip) 1f else 1.5f)
                        .fillMaxHeight()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Kanat Mizaç:",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = ptSansFont
                        )
                        
                        Text(
                            text = if (wingType > 0) "$wingType" else "-",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont
                        )
                    }
                }
                
                // Alt Tip Tab - Only show if different from Kanat Mizaç
                if (showAltTip) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Alt Tip:",
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontFamily = ptSansFont
                            )
                            
                            // Display the Alt Tip as a number, like the other tabs
                            Text(
                                text = "$wingTypePoint",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = harmonyHavenDarkGreenColor,
                                fontFamily = ptSansFont
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompatibilityDetail(
    title: String,
    percentage: Int,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenDarkGreenColor,
                fontFamily = ptSansFont
            )
            
            Text(
                text = "%$percentage",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenDarkGreenColor,
                fontFamily = ptSansFont
            )
        }
        
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(vertical = 2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFEEEEEE))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                harmonyHavenGreen,
                                harmonyHavenDarkGreenColor
                            )
                        )
                    )
            )
        }
        
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            fontFamily = ptSansFont,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


