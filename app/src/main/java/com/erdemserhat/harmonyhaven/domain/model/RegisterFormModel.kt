package com.erdemserhat.harmonyhaven.domain.model

import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.domain.model.ui.Gender
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import kotlinx.serialization.Serializable


@Serializable
data class RegisterFormModel(
    val name:String,
    val surname:String,
    val email:String,
    val password:String,
    val confirmPassword:String,
    val gender: Gender
)


fun RegisterFormModel.toUser(): User {
    return User(
        name,
        surname,
        email,
        password,
        gender.name,
        profilePhotoPath = "-",
        id = 0
    )
}

fun RegisterFormModel.toUserInformationSchema():UserInformationSchema{
    return UserInformationSchema(
        name = name,
        surname = surname,
        email = email,
        password = password,
        gender = gender.name

    )
}
