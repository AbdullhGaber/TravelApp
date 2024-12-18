package com.example.domain.repositories.auth

import com.example.domain.entity.TripUser

interface AuthRepository {
    fun register(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
}

interface AuthRemoteDataSource{
    fun register(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
}