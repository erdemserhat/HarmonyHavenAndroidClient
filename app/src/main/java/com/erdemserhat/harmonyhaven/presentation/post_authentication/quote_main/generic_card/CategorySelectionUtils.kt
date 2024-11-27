package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import androidx.compose.runtime.saveable.Saver

val categorySelectionSaver = Saver<CategorySelectionModel, List<Boolean>>(
    save = {
        listOf(
            it.isGeneralSelected,
            it.isLikedSelected,
            it.isBeYourselfSelected,
            it.isConfidenceSelected,
            it.isSelfImprovementSelected,
            it.isLifeSelected,
            it.isStrengthSelected,
            it.isPositivitySelected,
            it.isAnxietySelected,
            it.isSelfEsteemSelected,
            it.isSadnessSelected,
            it.isWorkSelected,
            it.isToxicRelationshipsSelected,
            it.isContinuingLifeSelected,
            it.isSeparationSelected,
            it.isCourageSelected,
            it.isSportSelected,
            it.isLoveSelected,
            it.isShortVideosSelected
        )
    },
    restore = {
        CategorySelectionModel(
            isGeneralSelected = it[0],
            isLikedSelected = it[1],
            isBeYourselfSelected = it[2],
            isConfidenceSelected = it[3],
            isSelfImprovementSelected = it[4],
            isLifeSelected = it[5],
            isStrengthSelected = it[6],
            isPositivitySelected = it[7],
            isAnxietySelected = it[8],
            isSelfEsteemSelected = it[9],
            isSadnessSelected = it[10],
            isWorkSelected = it[11],
            isToxicRelationshipsSelected = it[12],
            isContinuingLifeSelected = it[13],
            isSeparationSelected = it[14],
            isCourageSelected = it[15],
            isSportSelected = it[16],
            isLoveSelected = it[17],
            isShortVideosSelected = it[18]
        )
    }
)

data class CategorySelectionModel(
    var isGeneralSelected: Boolean = true,
    var isLikedSelected: Boolean = false,
    var isBeYourselfSelected: Boolean = false,
    var isConfidenceSelected: Boolean = false,
    var isSelfImprovementSelected: Boolean = false,
    var isLifeSelected: Boolean = false,
    var isStrengthSelected: Boolean = false,
    var isPositivitySelected: Boolean = false,
    var isAnxietySelected: Boolean = false,
    var isSelfEsteemSelected: Boolean = false,
    var isSadnessSelected: Boolean = false,
    var isContinuingLifeSelected: Boolean = false,
    var isWorkSelected: Boolean = false,
    var isToxicRelationshipsSelected: Boolean = false,
    var isSeparationSelected: Boolean = false,
    var isCourageSelected: Boolean = false,
    var isSportSelected: Boolean = false,
    var isLoveSelected: Boolean = false,
    var isShortVideosSelected:Boolean = false


) {
    fun nothingSelected(): Boolean {
        return !isGeneralSelected &&
                !isLikedSelected &&
                !isBeYourselfSelected &&
                !isConfidenceSelected &&
                !isSelfImprovementSelected &&
                !isLifeSelected &&
                !isStrengthSelected &&
                !isPositivitySelected &&
                !isAnxietySelected &&
                !isSelfEsteemSelected &&
                !isSadnessSelected &&
                !isWorkSelected &&
                !isToxicRelationshipsSelected &&
                !isContinuingLifeSelected &&
                !isSeparationSelected &&
                !isCourageSelected &&
                !isSportSelected &&
                !isLoveSelected &&
                !isShortVideosSelected
    }

    fun isOnlyGeneralSelected(): Boolean {
        return  isGeneralSelected &&
                !isLikedSelected &&
                !isBeYourselfSelected &&
                !isConfidenceSelected &&
                !isSelfImprovementSelected &&
                !isLifeSelected &&
                !isStrengthSelected &&
                !isPositivitySelected &&
                !isAnxietySelected &&
                !isSelfEsteemSelected &&
                !isSadnessSelected &&
                !isWorkSelected &&
                !isToxicRelationshipsSelected  &&
                !isShortVideosSelected
    }

    fun deselectGeneralIfOtherSelected() {
        if (isGeneralSelected &&
            (isLikedSelected || isBeYourselfSelected || isConfidenceSelected || isShortVideosSelected ||
                    isSelfImprovementSelected || isLifeSelected || isStrengthSelected || isPositivitySelected ||
                    isAnxietySelected || isSelfEsteemSelected  || isSadnessSelected
                    || isWorkSelected || isToxicRelationshipsSelected)
        ) {
            if (isLikedSelected) {
                isGeneralSelected = false

            }
        }
    }

}