package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatExperienceCustomizationScreen(navController: NavController) {
    var currentStep by remember { mutableStateOf(0) }
    val steps = listOf("Ülke", "Yaş", "İlişki Durumu", "İnanç Önemi", "İnanç", "Beklenti")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            ),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0xFFFBFBFC)
                        )
                    )
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Column {
                        Text(
                            text = "Harmonia'yı Özelleştir",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont
                        )

                        Text(
                            text = "Adım ${currentStep + 1}/${steps.size}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = ptSansFont
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (currentStep > 0) {
                                currentStep--
                            } else {
                                navController.navigateUp()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = harmonyHavenDarkGreenColor
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background( brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                ))
                .padding(24.dp)
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = { (currentStep + 1) / steps.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = harmonyHavenGreen,
                trackColor = Color.LightGray.copy(alpha = 0.3f),
            )

            Spacer(modifier = Modifier.height(32.dp))



                when (currentStep) {
                    0 -> CountrySelectionStep()
                    1 -> AgeSelectionStep()
                    2 -> RelationshipStatusStep()
                    3 -> FaithImportanceStep()
                    4 -> ReligionSelectionStep()
                    5 -> ExpectationSelectionStep()
                }

            // Question content


            Spacer(modifier = Modifier.weight(1f))

            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentStep > 0) {
                            currentStep--
                        } else {
                            navController.navigateUp()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(end = 8.dp),
                    border = BorderStroke(1.dp, harmonyHavenGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (currentStep > 0) "Önceki" else "İptal",
                        color = harmonyHavenGreen,
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = {
                        if (currentStep < steps.size - 1) {
                            currentStep++
                        } else {
                            navController.navigateUp() // Tamamlandığında ana ekrana dön
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (currentStep < steps.size - 1) "Sonraki" else "Tamamla",
                        color = Color.White,
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun CountrySelectionStep() {
    var selectedCountry by remember { mutableStateOf("") }
    val countries =
        listOf("Türkiye", "ABD", "Almanya", "Fransa", "Birleşik Krallık", "İtalya", "İspanya")

    Column(modifier = Modifier) {
        Text(
            text = "Hangi ülkedesin?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bulunduğunuz konuma göre daha uygun yanıtlar almak için ülkenizi seçin.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                countries.forEach { country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedCountry == country,
                                onClick = { selectedCountry = country }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCountry == country,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = country,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgeSelectionStep() {
    var selectedAgeRange by remember { mutableStateOf("") }
    val ageRanges = listOf("16-18", "19-24", "25-34", "35+")

    Column {
        Text(
            text = "Kaç yaşındasın?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Yaş aralığınızı seçerek size daha uygun tavsiyeler almanızı sağlayacağız.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                ageRanges.forEach { ageRange ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedAgeRange == ageRange,
                                onClick = { selectedAgeRange = ageRange }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedAgeRange == ageRange,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = ageRange,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RelationshipStatusStep() {
    var selectedStatus by remember { mutableStateOf("") }
    val relationshipStatuses =
        listOf("Bekar", "İlişkisi var", "Nişanlı", "Evli", "Boşanmış", "Belirtmek istemiyorum")

    Column {
        Text(
            text = "Mevcut ilişki durumun nedir?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "İlişki durumunuz hakkında bilgi vererek kişiselleştirilmiş ilişki tavsiyeleri alabilirsiniz.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                relationshipStatuses.forEach { status ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedStatus == status,
                                onClick = { selectedStatus = status }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedStatus == status,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = status,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FaithImportanceStep() {
    var selectedImportance by remember { mutableStateOf("") }
    val importanceLevels = listOf(
        "Çok önemli - Hayatımın merkezi",
        "Önemli - Düzenli olarak pratik ederim",
        "Biraz önemli - Zaman zaman pratik ederim",
        "Önemli değil - İnancım var ama pratik etmiyorum",
        "İnanmıyorum"
    )

    Column {
        Text(
            text = "İnançlar hayatında ne kadar önemli?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "İnanç ve değerlerinizin sizin için önem seviyesini belirtin.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                importanceLevels.forEach { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedImportance == level,
                                onClick = { selectedImportance = level }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedImportance == level,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = level,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReligionSelectionStep() {
    var selectedReligion by remember { mutableStateOf("") }
    val religions = listOf(
        "İslam",
        "Hristiyanlık",
        "Musevilik",
        "Budizm",
        "Hinduizm",
        "Spiritüel ama dindar değil",
        "Ateizm",
        "Agnostisizm",
        "Diğer",
        "Belirtmek istemiyorum"
    )

    Column {
        Text(
            text = "Mevcut inancın nedir?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "İnancınıza uygun tavsiyeler alabilmek için seçim yapın.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                religions.forEach { religion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedReligion == religion,
                                onClick = { selectedReligion = religion }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedReligion == religion,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = religion,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpectationSelectionStep() {
    var selectedExpectation by remember { mutableStateOf("") }
    val expectations = listOf(
        "Sadece dinle",
        "Duygularımı ve endişelerim konusunda yardım et",
        "Hedeflerime ulaşmamda yardımcı ol",
        "Günlük hayatımda beni motive et",
        "Pratik tavsiyelerde bulun",
        "Bilgi ve eğitim konusunda destek ol"
    )

    Column {
        Text(
            text = "Harmonia'dan ne bekliyorsun?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Harmonia'dan beklentilerinizi belirterek size nasıl yardımcı olabileceğimizi anlamamıza yardımcı olun.",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = ptSansFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .selectableGroup()
            ) {
                expectations.forEach { expectation ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = selectedExpectation == expectation,
                                onClick = { selectedExpectation = expectation }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedExpectation == expectation,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = harmonyHavenGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = expectation,
                            fontSize = 16.sp,
                            fontFamily = ptSansFont
                        )
                    }
                }
            }
        }
    }
} 