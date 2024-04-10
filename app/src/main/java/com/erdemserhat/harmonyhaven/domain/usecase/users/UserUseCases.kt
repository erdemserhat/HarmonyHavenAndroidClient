package com.erdemserhat.harmonyhaven.domain.usecase.users

data class UserUseCases(
    val deleteUSer:DeleteUser,
    val loginUser: LoginUser,
    val registerUser: RegisterUser,
    val updateUser: UpdateUser,
    val resetPasswordUser: ResetPasswordUserDev,
    val authenticateUser: AuthenticateUser,
    val fcmEnrolment: FcmEnrolment

)
