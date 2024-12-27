package com.example.domain.use_cases.auth

import com.example.domain.repositories.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val mAuthRepository: AuthRepository
){
    operator fun invoke(
        email : String,
        password : String,
        onSuccess : (String?) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mAuthRepository.login(email, password, onSuccess, onFailure)
    }
}