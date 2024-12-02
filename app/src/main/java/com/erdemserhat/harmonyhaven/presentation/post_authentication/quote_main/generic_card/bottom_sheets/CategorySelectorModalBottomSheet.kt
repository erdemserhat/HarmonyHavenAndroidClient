package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.CategorySelectionModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.categorySelectionSaver
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryPickerModalBottomSheet(
    sheetState: ModalBottomSheetState,
    onShouldFilterQuotes: (CategorySelectionModel, Boolean) -> Unit,
    onSaveCategorySelection: (CategorySelectionModel) -> Unit,
    onGetCategorySelectionModel: CategorySelectionModel,
    isLikedListEmpty: Boolean = false

) {
    var categoryPicker by rememberSaveable(stateSaver = categorySelectionSaver) {
        mutableStateOf(CategorySelectionModel())
    }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val updateList = {
        coroutineScope.launch {
            if (categoryPicker.nothingSelected()) {
                categoryPicker.isShortVideosSelected = true
            }

            onSaveCategorySelection(categoryPicker)
            onShouldFilterQuotes(categoryPicker, true)


        }
    }


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            categoryPicker = onGetCategorySelectionModel
            if (categoryPicker.isLikedSelected) {
                if (isLikedListEmpty) {
                    categoryPicker.isLikedSelected = false

                }
            }
            //updateList()

        }
    }


    ModalBottomSheetLayout(
        modifier = Modifier
            .zIndex(4f)
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
                        CategorySelectionMiniCard(
                            title = "Kısa Videolar",
                            icon = R.drawable.reels_2,
                            isSelected = categoryPicker.isShortVideosSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isShortVideosSelected = !categoryPicker.isShortVideosSelected,
                                )
                                updateList()
                            }
                        )


                        CategorySelectionMiniCard(
                            title = "Beğendiklerim",
                            icon = R.drawable.loved,
                            isSelected = categoryPicker.isLikedSelected,
                            onClick = {
                                //If category is liked and user clicks again, it will be unliked
                                if (categoryPicker.isLikedSelected) {
                                    categoryPicker = categoryPicker.copy(
                                        isLikedSelected = false,
                                    )
                                    return@CategorySelectionMiniCard

                                    //If category is unliked and user clicks again
                                } else {
                                    //and if liked list is not empty, it will be liked
                                    if (!isLikedListEmpty) {
                                        categoryPicker = categoryPicker.copy(
                                            isLikedSelected = true,

                                            )
                                        //but if liked list is empty
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Beğendiğiniz Gönderi Bulunmuyor.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                                updateList()





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
                                categoryPicker = categoryPicker.copy(
                                    isGeneralSelected = !categoryPicker.isGeneralSelected
                                )
                                updateList()


                            }
                        )


                        CategorySelectionMiniCard(
                            title = "Özgüven",
                            icon = R.drawable.self_confidence,
                            isSelected = categoryPicker.isConfidenceSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isConfidenceSelected = !categoryPicker.isConfidenceSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isSelfEsteemSelected = !categoryPicker.isSelfEsteemSelected,

                                    )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Kişisel Gelişim",
                            icon = R.drawable.experience,
                            isSelected = categoryPicker.isSelfImprovementSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSelfImprovementSelected = !categoryPicker.isSelfImprovementSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isLifeSelected = !categoryPicker.isLifeSelected,

                                )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Güç",
                            icon = R.drawable.protest,
                            isSelected = categoryPicker.isStrengthSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isStrengthSelected = !categoryPicker.isStrengthSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isPositivitySelected = !categoryPicker.isPositivitySelected,

                                )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Kaygıyla Başetme",
                            icon = R.drawable.dementia,
                            isSelected = categoryPicker.isAnxietySelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isAnxietySelected = !categoryPicker.isAnxietySelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isLoveSelected = !categoryPicker.isLoveSelected,


                                    )
                                updateList()
                            }
                        )


                        CategorySelectionMiniCard(
                            title = "Cesaret",
                            icon = R.drawable.reward,
                            isSelected = categoryPicker.isCourageSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isCourageSelected = !categoryPicker.isCourageSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isSadnessSelected = !categoryPicker.isSadnessSelected,

                                )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Spor",
                            icon = R.drawable.barbell,
                            isSelected = categoryPicker.isSportSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isSportSelected = !categoryPicker.isSportSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isWorkSelected = !categoryPicker.isWorkSelected,

                                )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Toksik İlişkiler",
                            icon = R.drawable.toxic,
                            isSelected = categoryPicker.isToxicRelationshipsSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isToxicRelationshipsSelected = !categoryPicker.isToxicRelationshipsSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isSeparationSelected = !categoryPicker.isSeparationSelected,

                                )
                                updateList()
                            }
                        )

                        CategorySelectionMiniCard(
                            title = "Kendine Gel",
                            icon = R.drawable.roundabout,
                            isSelected = categoryPicker.isContinuingLifeSelected,
                            onClick = {
                                categoryPicker = categoryPicker.copy(
                                    isContinuingLifeSelected = !categoryPicker.isContinuingLifeSelected,

                                )
                                updateList()
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
                                categoryPicker = categoryPicker.copy(
                                    isBeYourselfSelected = !categoryPicker.isBeYourselfSelected,
                                )
                                updateList()
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

        },
        content = {

        }
    )
}

@Composable
fun CategorySelectionMiniCard(
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