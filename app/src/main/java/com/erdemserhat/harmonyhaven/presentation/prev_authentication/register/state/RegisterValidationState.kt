package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state



/**
 * Error Codes
 *
 * - [201] Name is too short. It must be at least 2 characters long.
 * - [202] Name must contain only letters.
 * - [203] Surname is too short. It must be at least 2 characters long.
 * - [204] Surname must contain only letters.
 * - [205] Invalid email format.
 * - [206] There is already a registered user with this email.
 * - [207] Password must be at least 8 characters long.
 * - [208] Password must contain at least one uppercase letter, one lowercase letter, and one digit.
 * - [209] Password cannot contain the username. Please choose a different password.
 * - [210] Password cannot contain the surname. Please choose a different password.
 * - [211] Password cannot contain the email address. Please choose a different password.
 */


data class RegisterValidationState(
    val isNameValid:Boolean = true,
    val isSurnameValid:Boolean = true,
    val isEmailValid:Boolean = true,
    val isPasswordValid:Boolean = true
)

fun RegisterValidationState.getValidationStateByErrorCode(errorCode: Int): RegisterValidationState {
    return when (errorCode) {
        201,202 -> RegisterValidationState(isNameValid = false)
        203,204 -> RegisterValidationState(isSurnameValid = false)
        205,206 -> RegisterValidationState(isEmailValid = false)
        207,208,209,210,211 ->RegisterValidationState(isPasswordValid = false)
        else -> RegisterValidationState()


    }
}
