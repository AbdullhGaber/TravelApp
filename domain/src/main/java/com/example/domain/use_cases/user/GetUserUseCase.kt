package com.example.domain.use_cases.user

import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val mUserRepository: UserRepository
){
    operator fun invoke(
        uid : String,
        onSuccess : (TripUserEntity) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mUserRepository.getUser(uid, onSuccess, onFailure)
    }
}