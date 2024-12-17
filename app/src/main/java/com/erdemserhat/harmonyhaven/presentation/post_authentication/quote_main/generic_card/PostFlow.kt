package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.navigation.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.CategoryPickerModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun PostFlow(
    modifier: Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null
) {

    val quoteList1 = viewmodel.quotes.collectAsState()
    val categorySheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val shareQuoteSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

        val pagerState = rememberPagerState { quoteList1.value.size }

        Box(modifier = modifier) {

            // Bottom Sheet for Category Picker
            CategoryPickerModalBottomSheet(
                sheetState = categorySheetState,
                onShouldFilterQuotes = { selectedCategories ->
                    viewmodel.loadCategorizedQuotes(selectedCategories)
                },
                onSaveCategorySelection = {
                    viewmodel.saveCategorySelection(it)
                },
                onGetCategorySelectionModel = viewmodel.getCategorySelection()
            )

            if(quoteList1.value.isNotEmpty()){

                VerticalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    state = pagerState
                ) { page ->
                    val isCurrentPageVisible = pagerState.currentPage == page
                    val isPreviousPageVisible = pagerState.currentPage == page + 1
                    val isNextPageVisible = pagerState.currentPage == page - 1
                    val isPageVisible = isCurrentPageVisible || isPreviousPageVisible || isNextPageVisible

                    LaunchedEffect(page) {
                        if (page == quoteList1.value.size - 5) {
                            viewmodel.loadMoreQuote()
                        }
                        if(page==pagerState.pageCount){
                            viewmodel.loadMoreQuote()
                        }
                    }

                    Crossfade(targetState = quoteList1.value.toList()[page], label = "") { quote ->
                        Quote(
                            quote = quote,
                            currentScreen = pagerState.currentPage,
                            isVisible = isPageVisible,
                            isCurrentPage = isCurrentPageVisible,
                            modifier = Modifier.zIndex(2f),
                            viewmodel = viewmodel,
                            onShareQuoteClicked = {
                                coroutineScope.launch { shareQuoteSheetState.show() }
                            },
                            onCategoryClicked = {
                                coroutineScope.launch { categorySheetState.show() }
                            },
                            navController = navController
                        )
                    }
                }

            }else{
                // Loading Indicator when the quote list is empty
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    HarmonyHavenProgressIndicator()
                }
            }


        }


}
