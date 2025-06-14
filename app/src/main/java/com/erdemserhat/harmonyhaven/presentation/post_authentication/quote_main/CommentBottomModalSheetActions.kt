package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.CategorySelectionModel

class CommentBottomModalSheetActions(
    val onShouldFilterQuotes:(CategorySelectionModel)->Unit,
    val onSaveCategorySelection: (CategorySelectionModel) -> Unit,
    val onGetCategorySelectionModel: CategorySelectionModel

)
