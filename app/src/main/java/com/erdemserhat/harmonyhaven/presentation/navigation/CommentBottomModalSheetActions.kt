package com.erdemserhat.harmonyhaven.presentation.navigation

import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.CategorySelectionModel

class CommentBottomModalSheetActions(
    val onShouldFilterQuotes:(CategorySelectionModel)->Unit,
    val onSaveCategorySelection: (CategorySelectionModel) -> Unit,
    val onGetCategorySelectionModel: CategorySelectionModel

)

/**
 *   CategoryPickerModalBottomSheet(
 *             sheetState = categorySheetState,
 *             onShouldFilterQuotes = { selectedCategories ->
 *                 viewmodel.loadCategorizedQuotes(selectedCategories)
 *             },
 *             onSaveCategorySelection = {
 *                 viewmodel.saveCategorySelection(it)
 *             },
 *             onGetCategorySelectionModel = viewmodel.getCategorySelection()
 *         )
 */