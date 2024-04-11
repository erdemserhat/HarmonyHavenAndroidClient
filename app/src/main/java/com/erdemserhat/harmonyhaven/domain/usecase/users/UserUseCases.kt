package com.erdemserhat.harmonyhaven.domain.usecase.users

data class UserUseCases(
    val deleteUSer:DeleteUser,
    val loginUser: LoginUser,
    val registerUser: RegisterUser,
    val updateUser: UpdateUser,
    val authenticateUser: AuthenticateUser,
    val fcmEnrolment: FcmEnrolment,
    val sendPasswordResetMail: SendPasswordResetMail,
    val authenticatePasswordResetAttempt: AuthenticatePasswordResetAttempt,
    val completePasswordResetAttempt: CompletePasswordResetAttempt

)
