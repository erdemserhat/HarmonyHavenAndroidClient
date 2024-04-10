package com.erdemserhat.harmonyhaven.domain.validation

import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel


fun validateRegisterForm(form: RegisterFormModel){
    if (form.name.isEmpty()){
        throw IllegalArgumentException("Name field cannot be empty")
    }else if(form.surname.isEmpty()){
        throw IllegalArgumentException("Surname field cannot be empty")
    }else if(form.email.isEmpty()){
        throw IllegalArgumentException("Surname field cannot be empty")
    }else if(!isEmailValid(form.email)){
        throw IllegalArgumentException("Invalid email format")
    }else if(form.confirmPassword!=form.password){
        throw IllegalArgumentException("Password don't match")
    }else if(form.password.contains(form.name) || form.password.contains(form.name)){
        throw IllegalArgumentException("Password must not contain personal information")
    }
}
fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

fun areStringsEqual(string1: String, string2: String): Boolean {
    return string1 == string2
}



