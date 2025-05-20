package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ChatIntroScreen(navController: NavController) {
    // Color scheme
    val primaryColor = harmonyHavenGreen
    val darkPrimaryColor = harmonyHavenDarkGreenColor
    val lightGreen = harmonyHavenGreen.copy(alpha = 0.1f)
    val backgroundColor = Color.White
    val textColor = Color(0xFF333333)
    val cardColor = Color(0xFFF9F9F9)

    // State for showing the customization screen
    var showCustomizationScreen by remember { mutableStateOf(false) }

    when {
        showCustomizationScreen -> {
            CustomizationScreen(
                onBackClick = { showCustomizationScreen = false }
            )
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color(0xFFF5F7F9)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top section with title and icon
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(lightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.send_icon),
                                contentDescription = "Assistant",
                                modifier = Modifier.size(48.dp),
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Harmonia ile Tanışın",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = darkPrimaryColor,
                                textAlign = TextAlign.Center,
                                fontFamily = ptSansFont
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Size destek olmak ve tavsiye vermek için tasarlanmış özel asistanınız",
                            fontSize = 16.sp,
                            color = textColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontFamily = ptSansFont
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Harmonia Özellikleri card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Harmonia Özellikleri",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = darkPrimaryColor,
                                fontFamily = ptSansFont
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            BulletPoint(
                                "Enneagram kişilik analizi altyapısı",
                                primaryColor,
                                textColor
                            )
                            BulletPoint(
                                "Kişisel gelişim ve motivasyon konularında destek",
                                primaryColor,
                                textColor
                            )
                            BulletPoint(
                                "Günlük yaşam için pratik tavsiyeler ve yönlendirmeler",
                                primaryColor,
                                textColor
                            )
                            BulletPoint(
                                "Sorularınıza akıllı ve kişiselleştirilmiş yanıtlar",
                                primaryColor,
                                textColor
                            )
                        }
                    }

                    // Harmonia'yı Özelleştir card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { showCustomizationScreen = true },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Harmonia'yı Özelleştir",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = darkPrimaryColor,
                                    fontFamily = ptSansFont,
                                    modifier = Modifier.weight(1f)
                                )

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(lightGreen),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Customize",
                                        tint = primaryColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Kişiselleştirme size daha özel bir deneyim sunar. Birkaç basit soru yanıtlayarak Harmonia'nın size daha iyi hizmet etmesini sağlayabilirsiniz.",
                                fontSize = 15.sp,
                                color = textColor,
                                lineHeight = 22.sp,
                                fontFamily = ptSansFont
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                BulletPointSmall(
                                    "Kişiselleştirilmiş tavsiyeler",
                                    primaryColor,
                                    textColor
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                BulletPointSmall("Size özgü yanıtlar", primaryColor, textColor)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                BulletPointSmall(
                                    "Daha uyumlu bir sohbet deneyimi",
                                    primaryColor,
                                    textColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Bottom section with button
                    Button(
                        onClick = { navController.navigate(Screen.ChatScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        Text(
                            text = "Sohbete Başla",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontFamily = ptSansFont
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Start",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Harmonia ile ihtiyacınız olan her konuda sohbet edin",
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        fontFamily = ptSansFont
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun CustomizationScreen(onBackClick: () -> Unit) {
    var currentStep by remember { mutableStateOf(0) }
    val steps = listOf("Ülke", "Yaş", "İlişki Durumu", "İnanç Önemi", "İnanç", "Beklenti")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Top bar with back button and progress
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (currentStep > 0) {
                            currentStep--
                        } else {
                            onBackClick()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = harmonyHavenDarkGreenColor
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
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
            }

            // Progress bar
            LinearProgressIndicator(
                progress = (currentStep + 1) / steps.size.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = harmonyHavenGreen,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Question content
            when (currentStep) {
                0 -> CountrySelectionStep()
                1 -> AgeSelectionStep()
                2 -> RelationshipStatusStep()
                3 -> FaithImportanceStep()
                4 -> ReligionSelectionStep()
                5 -> ExpectationSelectionStep()
            }

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
                            onBackClick()
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
                            onBackClick() // Tamamlandığında ana ekrana dön
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

    Column {
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

@Composable
private fun BulletPoint(text: String, bulletColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(bulletColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ptSansFont
        )
    }
}

@Composable
private fun BulletPointSmall(text: String, bulletColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(bulletColor)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ptSansFont
        )
    }
}

@Composable
private fun ChatHistoryItem(title: String, timestamp: String, preview: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Sohbeti açma işlevi */ }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sol taraftaki renkli nokta göstergesi
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(harmonyHavenGreen)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Sağ taraftaki içerik
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Başlık ve zaman bilgisi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontFamily = ptSansFont
                )

                Text(
                    text = timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = ptSansFont
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Önizleme metni
            Text(
                text = preview,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = ptSansFont
            )
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    description: String,
    primaryColor: Color,
    surfaceColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = primaryColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryColor,
                    fontFamily = ptSansFont
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    lineHeight = 20.sp,
                    fontFamily = ptSansFont
                )
            }
        }
    }
}