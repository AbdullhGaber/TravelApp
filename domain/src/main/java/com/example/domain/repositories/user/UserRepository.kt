package com.example.domain.repositories.user


import android.net.Uri
import com.example.domain.entity.TripUserEntity

interface UserRepository {
    fun saveUser(
        user: TripUserEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
    fun getUser(
        uid : String,
        onSuccess : (TripUserEntity) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun uploadImage(
        fileName : String,
        uri: Uri?,
        onSuccess: (imagePath : String?, imageURL : String) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}

interface UserRemoteDataSource{
    fun saveUser(
        user: TripUserEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
    fun getUser(
        uid : String,
        onSuccess : (TripUserEntity) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun uploadImage(
        uri: Uri?,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}

interface UserOfflineDataSource{
    suspend fun saveUser(user: TripUserEntity)
    suspend fun getUser(uid : String) : TripUserEntity?
    fun saveUserImage(
        uri : Uri?,
        fileName : String,
        onSuccess: (String?) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}