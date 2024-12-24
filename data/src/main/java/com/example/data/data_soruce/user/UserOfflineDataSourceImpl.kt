package com.example.data.data_soruce.user

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.data.database.UserDao
import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.user.UserOfflineDataSource
import com.example.mapper.toEntity
import com.example.mapper.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class UserOfflineDataSourceImpl @Inject constructor(
    @ApplicationContext private val mContext: Context,
    private val userDao: UserDao
) : UserOfflineDataSource {
    override suspend fun saveUser(user: TripUserEntity) {
        val userModel = user.toModel()
        userDao.insertUser(userModel)
        Log.e("Room" , "user saved in room successfully")
    }

    override suspend fun getUser(uid : String) : TripUserEntity?{
        return userDao.getUserById(uid)?.toEntity()
    }

    override fun saveUserImage(
        uri: Uri?,
        fileName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (Throwable) -> Unit,
    ){
        try {
            if(uri == null) {
                onSuccess(null)
                return
            }

            val inputStream = mContext.contentResolver.openInputStream(uri)

            val file = File(mContext.cacheDir,fileName)

            inputStream.use{inStream ->
                file.outputStream().use{outStream ->
                    inStream?.copyTo(outStream)
                }
            }
            onSuccess(file.absolutePath)
        }catch (e : Exception){
            onFailure(e)
        }
    }
}