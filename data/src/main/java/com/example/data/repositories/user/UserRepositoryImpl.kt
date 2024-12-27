package com.example.data.repositories.user

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.data.uitls.NetworkUtil
import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.user.UserOfflineDataSource
import com.example.domain.repositories.user.UserRemoteDataSource
import com.example.domain.repositories.user.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val mContext: Context,
    private val mUserRemoteDataSource: UserRemoteDataSource,
    private val mUserOfflineDataSource: UserOfflineDataSource
): UserRepository {
    override fun saveUser(
        user: TripUserEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ){
        mUserRemoteDataSource.saveUser(
            user = user,
            onSuccess = {
                runBlocking {
                    mUserOfflineDataSource.saveUser(user)
                    onSuccess()
                }
            },
            onFailure = onFailure
        )
    }

    override fun getUser(
        uid: String,
        onSuccess: (TripUserEntity) -> Unit,
        onFailure: (Throwable) -> Unit,
    ){
        if(NetworkUtil.isDeviceConnected(mContext)){
            mUserRemoteDataSource.getUser(uid, onSuccess, onFailure)
        }

        runBlocking{
            val user = mUserOfflineDataSource.getUser(uid)
            user?.let{ onSuccess(it)}
        }
    }

    override fun uploadImage(
        fileName : String,
        uri: Uri?,
        onSuccess: (imagePath : String?, imageURL : String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        mUserRemoteDataSource.uploadImage(
            uri,
            onSuccess = { imageURL ->
                mUserOfflineDataSource.saveUserImage(
                    uri = uri,
                    fileName = fileName,
                    onSuccess = { imagePath ->
                        onSuccess(imagePath,imageURL) // ViewModel callback
                    },
                    onFailure = onFailure
                )
            },
            onFailure = onFailure
        )
    }
}