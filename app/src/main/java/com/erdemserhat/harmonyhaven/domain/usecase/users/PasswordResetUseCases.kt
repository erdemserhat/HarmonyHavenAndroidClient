package com.erdemserhat.harmonyhaven.domain.usecase.users

data class PasswordResetUseCases(
    val sendPasswordResetMail:SendPasswordResetMail,
    val authenticatePasswordResetAttempt:AuthenticatePasswordResetAttempt,
    val completePasswordResetAttempt:CompletePasswordResetAttempt,
)
