package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamousPeopleScreen(navController: NavController, famousPeople: List<EnneagramFamousPeople>) {
    BackHandler {
        navController.popBackStack()
    }
    
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    
    val scaffoldState = rememberScaffoldState()
    
    // State for tracking selected person for popup
    var selectedPerson by remember { mutableStateOf<EnneagramFamousPeople?>(null) }
    
    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFF8F8F8),
        topBar = {
            Column {
                // Add a spacer for the status bar height
                Spacer(modifier = Modifier.fillMaxWidth().height(statusBarHeight).background(Color.White))
 
                // App Bar
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = "Ünlü Enneagram Kişilikleri",
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Geri",
                                tint = harmonyHavenDarkGreenColor
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        // Main Content
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(famousPeople) { person ->
                FamousPersonGridCard(
                    person = person, 
                    onClick = { selectedPerson = person }
                )
            }
        }
    }
    
    // Show bottom sheet when a person is selected
    if (selectedPerson != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedPerson = null },
            sheetState = bottomSheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            PersonDetailContent(person = selectedPerson!!)
        }
    }
}

@Composable
fun FamousPersonGridCard(
    person: EnneagramFamousPeople,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = person.desc,
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontFamily = ptSansFont,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PersonDetailContent(person: EnneagramFamousPeople) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile image
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = person.imageUrl)
                    .build()
            ),
            contentDescription = person.name,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Name
        Text(
            text = person.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Description (full text)
        Text(
            text = person.desc,
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontFamily = ptSansFont,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(40.dp))
    }
} 