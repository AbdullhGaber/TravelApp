package com.example.travelapp.utils


import android.util.Patterns
import androidx.compose.runtime.MutableState
import com.example.data.uitls.mContext
import com.example.travelapp.R


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
        rePasswordError.value = mContext.getString(R.string.re_type_password_field_is_required)
        return false
    }

    if (rePassword != password) {
        rePasswordError.value = mContext.getString(R.string.passwords_entered_are_not_compatible)
        return false
    }

    return true
}

fun isPasswordValid(
    password: String,
    passwordError: MutableState<String>,
): Boolean {
    if (password.isEmpty()) {
        passwordError.value = mContext.getString(R.string.password_field_is_required)
        return false
    }

    if (password.length < 6) {
        passwordError.value = mContext.getString(R.string.password_field_must_be_min_of_6_characters)
        return false
    }

    return true
}

fun isPhoneNoValid(
    phoneNo: String,
    phoneNoError: MutableState<String>,
): Boolean {
    if (phoneNo.isEmpty()) {
        phoneNoError.value = mContext.getString(R.string.phone_field_is_required)
        return false
    }
    return true
}

fun isUsernameValid(
    userName: String,
    userNameError: MutableState<String>,
): Boolean {
    if (userName.isEmpty()) {
        userNameError.value = mContext.getString(R.string.username_field_is_required)
        return false
    }
    return true
}

fun isEmailValid(
    email: String,
    emailError: MutableState<String>,
): Boolean {
    if (email.isEmpty()) {
        emailError.value = mContext.getString(R.string.email_field_is_required)
        return false
    }

    if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
        emailError.value = mContext.getString(R.string.email_format_is_not_correct)
        return false
    }

    return true
}