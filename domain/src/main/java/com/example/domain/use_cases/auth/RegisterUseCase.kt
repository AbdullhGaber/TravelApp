package com.example.domain.use_cases.auth

import com.example.domain.repositories.auth.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val mAuthRepository: AuthRepository
) {
    operator fun invoke(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mAuthRepository.register(email, password, onSuccess, onFailure)
    }
}