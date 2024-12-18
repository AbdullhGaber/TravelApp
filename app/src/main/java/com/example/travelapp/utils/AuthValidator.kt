package com.example.travelapp.utils

import android.util.Patterns
import androidx.compose.runtime.MutableState

fun areRegisterFieldsValid(
    email : String,
    emailError: MutableState<String>,
    userName : String,
    userNameError : MutableState<String>,
    phoneNo :String,
    phoneNoError : MutableState<String>,
    password : String,
    passwordError : MutableState<String>,
    rePassword : String,
    rePasswordError : MutableState<String>
) : Boolean{

    if(!isEmailValid(email, emailError)) return false

    if(!isUsernameValid(userName, userNameError)) return false

    if(!isPhoneNoValid(phoneNo, phoneNoError)) return false

    if(!isPasswordValid(password, passwordError)) return false

    if(!isRePasswordValid(rePassword, rePasswordError, password)) return false

    return true
}

fun isRePasswordValid(
    rePassword: String,
    rePasswordError: MutableState<String>,
    password: String,
): Boolean {
    if (rePassword.isEmpty()) {
        rePasswordError.value = "Re-type password field is required"
        return false
    }

    if (rePassword != password) {
        rePasswordError.value = "Passwords entered are not compatible"
        return false
    }

    return true
}

fun isPasswordValid(
    password: String,
    passwordError: MutableState<String>,
): Boolean {
    if (password.isEmpty()) {
        passwordError.value = "Password field is required"
        return false
    }

    if (password.length < 6) {
        passwordError.value = "Password field must be min of 6 characters"
        return false
    }

    return true
}

fun isPhoneNoValid(
    phoneNo: String,
    phoneNoError: MutableState<String>,
): Boolean {
    if (phoneNo.isEmpty()) {
        phoneNoError.value = "Phone field is required"
        return false
    }
    return true
}

fun isUsernameValid(
    userName: String,
    userNameError: MutableState<String>,
): Boolean {
    if (userName.isEmpty()) {
        userNameError.value = "Username field is required"
        return false
    }
    return true
}

fun isEmailValid(
    email: String,
    emailError: MutableState<String>,
): Boolean {
    if (email.isEmpty()) {
        emailError.value = "Email field is required"
        return false
    }

    if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
        emailError.value = "Email format is not correct"
        return false
    }

    return true
}