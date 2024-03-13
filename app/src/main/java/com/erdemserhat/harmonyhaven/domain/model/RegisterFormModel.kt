package com.erdemserhat.harmonyhaven.domain.model

data class RegisterFormModel(
    val name:String,
    val surname:String,
    val email:String,
    val password:String,
    val confirmPassword:String,
    val gender:Gender
)


fun RegisterFormModel.toUser():User{
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
