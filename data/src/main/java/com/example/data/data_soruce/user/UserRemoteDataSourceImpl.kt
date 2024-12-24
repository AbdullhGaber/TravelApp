package com.example.data.data_soruce.user


import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.data.model.ImageResponseModel
import com.example.data.web_services.image.ImgurService
import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.user.UserRemoteDataSource
import com.example.mapper.toEntity
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val mContext: Context,
    private val mFireStore : FirebaseFirestore,
    private val mImgurService : ImgurService
) : UserRemoteDataSource {
    override fun saveUser(
        user: TripUserEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    ) {
        val userDocRef = mFireStore.collection(TripUserEntity.USER_COLLECTION).document()

        user.uid = userDocRef.id

        userDocRef.set(user)
            .addOnSuccessListener {
                Log.e("FIB Auth" , "User saved in firestore")
                onSuccess()
            }.addOnFailureListener {
                Log.e("FIB Auth", "Error : ${it.message}")
                onFailure(it)
            }
    }

    override fun getUser(
        uid : String,
        onSuccess : (TripUserEntity) -> Unit,
        onFailure : (Throwable) -> Unit
    ) {
        val userSnapshot = mFireStore.collection(TripUserEntity.USER_COLLECTION).document(uid).get()

        userSnapshot.addOnSuccessListener { result ->
            val user = result.toObject(TripUserEntity::class.java)
            if(user != null){
                onSuccess(user)
                Log.e("FIB Auth" , "User retrieved successfully")
            }else{
                onFailure(Exception("Something wrong happened when trying to get the user data"))
                Log.e("FIB Auth" , "Error : User is null")
            }
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB Auth" , "Error : ${it.message}")
        }
    }

    override fun uploadImage(
        uri: Uri?,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        if(uri == null) {
            onSuccess("")
            return
        }

        val byteArray = mContext.contentResolver.openInputStream(uri)?.readBytes()

        val bodyRequest = byteArray?.toRequestBody("image/jpeg".toMediaTypeOrNull())

        mImgurService.uploadImage(bodyRequest!!).enqueue(
            object : retrofit2.Callback<ImageResponseModel>{
                override fun onResponse(
                    call: retrofit2.Call<ImageResponseModel>,
                    response: retrofit2.Response<ImageResponseModel>,
                ) {
                    if(response.isSuccessful){
                        val imageURL = response.body()?.toEntity()?.data?.link?: ""
                        onSuccess(imageURL)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ImageResponseModel>, t: Throwable) {
                    onFailure(t)
                    Log.e("Imgur" , "Error : ${t.message}")
                }
            }
        )
    }
}