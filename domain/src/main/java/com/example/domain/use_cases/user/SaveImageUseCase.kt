package com.example.domain.use_cases.user

import android.content.ContentResolver
import android.net.Uri
import com.example.domain.repositories.user.UserRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val mUserRepository: UserRepository
) {
    operator fun invoke(
        fileName : String,
        uri: Uri?,
        onSuccess: (String? , String) -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        mUserRepository.uploadImage(fileName, uri, onSuccess, onFailure)
    }
}

