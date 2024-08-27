package com.erdemserhat.harmonyhaven.presentation.post_authentication.article



/**
 * The `ArticleScreenConstants` class contains constant values used in the article screen.
 * This class defines the minimum, maximum, and default font sizes for the text displayed on the article screen.
 *
 * The constants are defined inside a `companion object` to make them accessible directly via the class name,
 * without needing to instantiate the class. This pattern ensures that the constants are tied to the class itself
 * and are available globally wherever the class is accessible.
 *
 * @property MIN_FONT_SIZE The minimum font size allowed in the article screen.
 * @property MAX_FONT_SIZE The maximum font size allowed in the article screen.
 * @property DEFAULT_FONT_SIZE The default font size used in the article screen.
 */
class ArticleScreenConstants {
    companion object {
        const val MIN_FONT_SIZE: Int = 10
        const val MAX_FONT_SIZE: Int = 24
        const val DEFAULT_FONT_SIZE: Int = 16
    }
}


