package com.erdemserhat.harmonyhaven.domain.validation

import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel

/**
 * Validates the registration form.
 *
 * @param form The [RegisterFormModel] instance containing the registration data.
 * @throws IllegalArgumentException If any of the validation checks fail.
 */
fun validateRegisterForm(form: RegisterFormModel) {
    // Check if name is empty
    if (form.name.isEmpty()) {
        throw IllegalArgumentException("Name field cannot be empty")
    }
    // Check if surname is empty
    else if (form.surname.isEmpty()) {
        throw IllegalArgumentException("Surname field cannot be empty")
    }
    // Check if email is empty
    else if (form.email.isEmpty()) {
        throw IllegalArgumentException("Email field cannot be empty")
    }
    // Validate email format
    else if (!isEmailValid(form.email)) {
        throw IllegalArgumentException("Invalid email format")
    }
    // Check if password and confirm password match
    else if (form.confirmPassword != form.password) {
        throw IllegalArgumentException("Passwords don't match")
    }
    // Check if password contains personal information
    else if (form.password.contains(form.name, ignoreCase = true) || form.password.contains(form.surname, ignoreCase = true)) {
        throw IllegalArgumentException("Password must not contain personal information")
    }
}

/**
 * Checks if the given email is in a valid format.
 *
 * @param email The email address to check.
 * @return True if the email is valid, false otherwise.
 */
fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}
