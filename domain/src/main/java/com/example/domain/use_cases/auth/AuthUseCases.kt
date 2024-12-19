package com.example.domain.use_cases.auth

data class AuthUseCases(
    val registerUseCase : RegisterUseCase,
    val loginUseCase: LoginUseCase
)
