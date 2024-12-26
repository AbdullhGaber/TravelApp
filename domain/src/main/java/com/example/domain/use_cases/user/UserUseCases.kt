package com.example.domain.use_cases.user

data class UserUseCases(
    val getUserUseCase : GetUserUseCase,
    val saveUserUseCase : SaveUserUseCase,
    val saveImageUseCase: SaveImageUseCase
)