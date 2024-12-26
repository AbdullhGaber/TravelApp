package com.example.domain.repositories.auth

interface AuthRepository {
    fun register(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun login(
        email : String,
        password : String,
        onSuccess : (String?) -> Unit,
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

    fun login(
        email : String,
        password : String,
        onSuccess : (String?) -> Unit,
        onFailure : (Throwable) -> Unit
    )
}