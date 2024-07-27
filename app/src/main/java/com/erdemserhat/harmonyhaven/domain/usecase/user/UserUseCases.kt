package com.erdemserhat.harmonyhaven.domain.usecase.user

import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.AuthenticatePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.CompletePasswordResetAttempt
import com.erdemserhat.harmonyhaven.domain.usecase.password_reset.SendPasswordResetMail

data class UserUseCases(
    val registerUser: RegisterUser,
    val authenticateUser: AuthenticateUser,
    val fcmEnrolment: FcmEnrolment,
    val sendPasswordResetMail: SendPasswordResetMail,
    val authenticatePasswordResetAttempt: AuthenticatePasswordResetAttempt,
    val completePasswordResetAttempt: CompletePasswordResetAttempt,
    val checkUserAuthenticationStatus: CheckUserAuthenticationStatus,
    val getUserInformation: GetUserInformation,
    val updateUserInformation: UpdateUserInformation

)
