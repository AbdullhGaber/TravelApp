package com.example.domain.use_cases

import com.example.domain.repositories.auth.AuthRepository
import com.example.domain.use_cases.auth.AuthUseCases
import com.example.domain.use_cases.auth.LoginUseCase
import com.example.domain.use_cases.auth.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @Provides
    fun provideAuthUseCases(
        authRepository: AuthRepository
    ) : AuthUseCases {
        return AuthUseCases(
            registerUseCase = RegisterUseCase(authRepository),
            loginUseCase = LoginUseCase(authRepository)
        )
    }
}