package com.example.data.repositories.auth

import com.example.domain.repositories.auth.AuthRemoteDataSource
import com.example.domain.repositories.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val mRemoteAuthRemoteDataSource: AuthRemoteDataSource
): AuthRepository {
    override fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        mRemoteAuthRemoteDataSource.register(email, password, onSuccess, onFailure)
    }

    override fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        mRemoteAuthRemoteDataSource.login(email, password, onSuccess, onFailure)
    }
}