package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.CommentBottomModalSheetActions
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.CategorySelectionModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.categorySelectionSaver
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryPickerModalBottomSheet(
    sheetState: SheetState,
    actions: CommentBottomModalSheetActions,
    onDismissRequest: () -> Unit
) {
    var categoryPicker by rememberSaveable(stateSaver = categorySelectionSaver) {
        mutableStateOf(CategorySelectionModel())
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    //load category preferences.
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            categoryPicker = actions.onGetCategorySelectionModel

        }
    }

    var lastSelectedState by remember { mutableStateOf(categoryPicker) }

    var debounceJob: Job? = null

    val updateList = {
        coroutineScope.launch {
            if (categoryPicker.nothingSelected()) {
                categoryPicker.isShortVideosSelected = true
            }
            actions.onSaveCategorySelection(categoryPicker)
            actions.onShouldFilterQuotes(categoryPicker)


        }
    }

    fun handleCategorySelection(updatedCategoryPicker: CategorySelectionModel) {
        categoryPicker = updatedCategoryPicker
        lastSelectedState = updatedCategoryPicker
        debounceJob?.cancel() // Önceki gecikmeyi iptal et
        debounceJob = coroutineScope.launch {
            delay(500L) // 500ms gecikme
            updateList() //
        }
    }



    ModalBottomSheet(
        containerColor = Color.Black,
        modifier = Modifier.statusBarsPadding(),
        scrimColor = Color.Black.copy(alpha = 0.4f),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest


    ) {

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
            Text(
                "Kategoriler", color = Color.White, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp), fontSize = 15.sp
            )

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Kısa Videolar",
                        icon = R.drawable.reels_2,
                        isSelected = categoryPicker.isShortVideosSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isShortVideosSelected = !categoryPicker.isShortVideosSelected,
                                )
                            )

                            //updateList()
                        }
                    )


                    CategorySelectionMiniCard(
                        title = "Beğendiklerim",
                        icon = R.drawable.loved,
                        isSelected = categoryPicker.isLikedSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isLikedSelected = !categoryPicker.isLikedSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    CategorySelectionMiniCard(
                        title = "Genel",
                        icon = R.drawable.chat,
                        isSelected = categoryPicker.isGeneralSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isGeneralSelected = !categoryPicker.isGeneralSelected,
                                )
                            )


                        }
                    )


                    CategorySelectionMiniCard(
                        title = "Özgüven",
                        icon = R.drawable.self_confidence,
                        isSelected = categoryPicker.isConfidenceSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isConfidenceSelected = !categoryPicker.isConfidenceSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Öz Saygı",
                        icon = R.drawable.self_motivation,
                        isSelected = categoryPicker.isSelfEsteemSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isSelfEsteemSelected = !categoryPicker.isSelfEsteemSelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Kişisel Gelişim",
                        icon = R.drawable.experience,
                        isSelected = categoryPicker.isSelfImprovementSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isSelfImprovementSelected = !categoryPicker.isSelfImprovementSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Yaşam",
                        icon = R.drawable.wellness,
                        isSelected = categoryPicker.isLifeSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isLifeSelected = !categoryPicker.isLifeSelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Güç",
                        icon = R.drawable.protest,
                        isSelected = categoryPicker.isStrengthSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isStrengthSelected = !categoryPicker.isStrengthSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Pozitif Düşünmek",
                        icon = R.drawable.positive_thinking,
                        isSelected = categoryPicker.isPositivitySelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isPositivitySelected = !categoryPicker.isPositivitySelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Kaygıyla Başetme",
                        icon = R.drawable.dementia,
                        isSelected = categoryPicker.isAnxietySelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isAnxietySelected = !categoryPicker.isAnxietySelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    /////
                    CategorySelectionMiniCard(
                        title = "Aşk",
                        icon = R.drawable.dove,
                        isSelected = categoryPicker.isLoveSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isLoveSelected = !categoryPicker.isLoveSelected,
                                )
                            )
                        }
                    )


                    CategorySelectionMiniCard(
                        title = "Cesaret",
                        icon = R.drawable.reward,
                        isSelected = categoryPicker.isCourageSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isCourageSelected = !categoryPicker.isCourageSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Üzüntü",
                        icon = R.drawable.sad,
                        isSelected = categoryPicker.isSadnessSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isSadnessSelected = !categoryPicker.isSadnessSelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Spor",
                        icon = R.drawable.barbell,
                        isSelected = categoryPicker.isSportSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isSportSelected = !categoryPicker.isSportSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "İş",
                        icon = R.drawable.work,
                        isSelected = categoryPicker.isWorkSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isWorkSelected = !categoryPicker.isWorkSelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Toksik İlişkiler",
                        icon = R.drawable.toxic,
                        isSelected = categoryPicker.isToxicRelationshipsSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isToxicRelationshipsSelected = !categoryPicker.isToxicRelationshipsSelected,
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySelectionMiniCard(
                        title = "Ayrılık",
                        icon = R.drawable.hearts,
                        isSelected = categoryPicker.isSeparationSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isSeparationSelected = !categoryPicker.isSeparationSelected,
                                )
                            )
                        }
                    )

                    CategorySelectionMiniCard(
                        title = "Kendine Gel",
                        icon = R.drawable.roundabout,
                        isSelected = categoryPicker.isContinuingLifeSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isContinuingLifeSelected = !categoryPicker.isContinuingLifeSelected,
                                )
                            )
                        }
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    CategorySelectionMiniCard(
                        title = "Kendini Sevmek",
                        icon = R.drawable.love_yourself,
                        isSelected = categoryPicker.isBeYourselfSelected,
                        onClick = {
                            handleCategorySelection(
                                categoryPicker.copy(
                                    isBeYourselfSelected = !categoryPicker.isBeYourselfSelected,
                                )
                            )
                        }
                    )



                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(80.dp)
                            .padding(8.dp)

                    )


                }
            }


        }


    }

}

@Composable
fun CategorySelectionMiniCard(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    var lastClickTime by remember { mutableLongStateOf(0L) } // Son tıklama zamanını takip et
    val debounceTime = 500L // Gecikme süresi (ms)

    Card(
        modifier = modifier
            .width(180.dp)
            .height(80.dp) // Kartın yüksekliğini belirliyoruz
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > debounceTime) { // Gecikmeyi kontrol et
                    lastClickTime = currentTime // Son tıklama zamanını güncelle
                    onClick()
                }
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
