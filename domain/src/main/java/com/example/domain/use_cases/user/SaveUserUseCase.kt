package com.example.domain.use_cases.user

import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val mUserRepository: UserRepository
) {
    operator fun invoke(
        user : TripUserEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mUserRepository.saveUser(user,onSuccess, onFailure)
    }
}