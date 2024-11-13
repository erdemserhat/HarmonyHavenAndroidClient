package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor

// Define the Saver
val categorySelectionSaver = Saver<CategorySelectionModel, List<Boolean>>(
    save = { listOf(
        it.isGeneralSelected,
        it.isLikedSelected,
        it.isBeYourselfSelected,
        it.isConfidenceSelected,
        it.isShortQuotesSelected,
        it.isSelfImprovementSelected,
        it.isLifeSelected,
        it.isStrengthSelected,
        it.isPositivitySelected,
        it.isAnxietySelected,
        it.isSelfEsteemSelected,
        it.isSelfLoveSelected,
        it.isSadnessSelected,
        it.isHeartBrokenSelected,
        it.isWorkSelected,
        it.isToxicRelationshipsSelected
    ) },
    restore = {
        CategorySelectionModel(
            isGeneralSelected = it[0],
            isLikedSelected = it[1],
            isBeYourselfSelected = it[2],
            isConfidenceSelected = it[3],
            isShortQuotesSelected = it[4],
            isSelfImprovementSelected = it[5],
            isLifeSelected = it[6],
            isStrengthSelected = it[7],
            isPositivitySelected = it[8],
            isAnxietySelected = it[9],
            isSelfEsteemSelected = it[10],
            isSelfLoveSelected = it[11],
            isSadnessSelected = it[12],
            isHeartBrokenSelected = it[13],
            isWorkSelected = it[14],
            isToxicRelationshipsSelected = it[15]
        )
    }
)



data class CategorySelectionModel(
    var isGeneralSelected: Boolean = true,
    var isLikedSelected: Boolean = false,
    var isBeYourselfSelected: Boolean = false,
    var isConfidenceSelected: Boolean = false,
    var isShortQuotesSelected: Boolean = false,
    var isSelfImprovementSelected: Boolean = false,
    var isLifeSelected: Boolean = false,
    var isStrengthSelected: Boolean = false,
    var isPositivitySelected: Boolean = false,
    var isAnxietySelected: Boolean = false,
    var isSelfEsteemSelected: Boolean = false,
    var isSelfLoveSelected: Boolean = false,
    var isSadnessSelected: Boolean = false,
    var isHeartBrokenSelected: Boolean = false,
    var isWorkSelected: Boolean = false,
    var isToxicRelationshipsSelected: Boolean = false,

    ){

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryPickerModalBottomSheet(
    sheetState: ModalBottomSheetState,
    onShouldFilterQuotes:(CategorySelectionModel)->Unit

) {

    var categoryPicker by rememberSaveable(stateSaver = categorySelectionSaver) {
        mutableStateOf(CategorySelectionModel())
    }

    LaunchedEffect(categoryPicker){
        Log.d("dsadsa", categoryPicker.toString())

        onShouldFilterQuotes(categoryPicker)

    }



    ModalBottomSheetLayout(
        modifier = Modifier
            .zIndex(3f)
            .background(Color.Transparent),
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.4f),
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black, shape = RoundedCornerShape(
                            topEnd = 15.dp,
                            topStart = 15.dp
                        )
                    )

            ) {
                Divider(
                    color = Color.Gray, // Çizginin rengi
                    thickness = 2.dp, // Çizginin kalınlığı
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .size(width = 45.dp, height = 5.dp)
                        .background(color = Color.White)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    "Kategoriler", color = Color.White, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp), fontSize = 15.sp
                )

                Divider(
                    color = Color.White.copy(alpha = 0.2f), // Çizginin rengi
                    thickness = 0.8.dp, // Çizginin kalınlığı
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .align(Alignment.CenterHorizontally)
                )

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Genel",
                            icon = R.drawable.spiritual,
                            isSelected = categoryPicker.isGeneralSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isGeneralSelected = !categoryPicker.isGeneralSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Beğendiklerim",
                            icon = R.drawable.favorites,
                            isSelected = categoryPicker.isLikedSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isLikedSelected = !categoryPicker.isLikedSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Kendin Ol",
                            icon = R.drawable.beyourself,
                            isSelected = categoryPicker.isBeYourselfSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isBeYourselfSelected = !categoryPicker.isBeYourselfSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Özgüven",
                            icon = R.drawable.confidence,
                            isSelected = categoryPicker.isConfidenceSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isConfidenceSelected = !categoryPicker.isConfidenceSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Kısa Alıntılar",
                            icon = R.drawable.short_quotes,
                            isSelected = categoryPicker.isShortQuotesSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isShortQuotesSelected = !categoryPicker.isShortQuotesSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Kişisel Gelişim",
                            icon = R.drawable.self_improvement,
                            isSelected = categoryPicker.isSelfImprovementSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSelfImprovementSelected = !categoryPicker.isSelfImprovementSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Yaşam",
                            icon = R.drawable.life,
                            isSelected = categoryPicker.isLifeSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isLifeSelected = !categoryPicker.isLifeSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Güç",
                            icon = R.drawable.strength,
                            isSelected = categoryPicker.isStrengthSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isStrengthSelected = !categoryPicker.isStrengthSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Pozitif Düşünmek",
                            icon = R.drawable.positivity,
                            isSelected = categoryPicker.isPositivitySelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isPositivitySelected = !categoryPicker.isPositivitySelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Kaygıyla Başetme",
                            icon = R.drawable.anxiety,
                            isSelected = categoryPicker.isAnxietySelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isAnxietySelected = !categoryPicker.isAnxietySelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Öz Saygı",
                            icon = R.drawable.selfesteem,
                            isSelected = categoryPicker.isSelfEsteemSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSelfEsteemSelected = !categoryPicker.isSelfEsteemSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "İzzetinefis",
                            icon = R.drawable.selflove,
                            isSelected = categoryPicker.isSelfLoveSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSelfLoveSelected = !categoryPicker.isSelfLoveSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "Üzüntü",
                            icon = R.drawable.sadness,
                            isSelected = categoryPicker.isSadnessSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSadnessSelected = !categoryPicker.isSadnessSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Kalbi Kırık",
                            icon = R.drawable.heartbroken,
                            isSelected = categoryPicker.isHeartBrokenSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isHeartBrokenSelected = !categoryPicker.isHeartBrokenSelected
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        QuoteCategory(
                            title = "İş",
                            icon = R.drawable.work,
                            isSelected = categoryPicker.isWorkSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isWorkSelected = !categoryPicker.isWorkSelected
                                )
                            }
                        )

                        QuoteCategory(
                            title = "Toksik İlişkiler",
                            icon = R.drawable.toxicrelationships,
                            isSelected = categoryPicker.isToxicRelationshipsSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isToxicRelationshipsSelected = !categoryPicker.isToxicRelationshipsSelected
                                )
                            }
                        )
                    }
                }


            }

        },
        content = {

        }
    )
}

@Composable
fun QuoteCategory(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .height(80.dp) // Kartın yüksekliğini belirliyoruz
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) harmonyHavenGreen else Color.DarkGray.copy(alpha = 0.5f),
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Solda yazı
            Text(
                text = title,
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier.weight(1f) // Yazı, boş alanı doldurur
            )

            // Sağda simge
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}