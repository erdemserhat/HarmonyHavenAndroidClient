package com.erdemserhat.harmonyhaven.domain.usecase.password_reset

data class PasswordResetUseCases(
    val sendPasswordResetMail: SendPasswordResetMail,
    val authenticatePasswordResetAttempt: AuthenticatePasswordResetAttempt,
    val completePasswordResetAttempt: CompletePasswordResetAttempt,
)
