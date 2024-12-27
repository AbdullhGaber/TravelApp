package com.example.travelapp.screens.navigator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.domain.manager.LocalUserManager
import com.example.domain.use_cases.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigatorViewModel @Inject constructor(
    private val mLocalUserManager : LocalUserManager,
    private val mUserUserUseCases: UserUseCases
) : ViewModel() {
    init{
        viewModelScope.launch {
            mLocalUserManager.getUserUID().collect{ uid ->
                uid?.let{getUser(uid)}
            }
        }
    }

    private fun getUser(uid : String){
        mUserUserUseCases.getUserUseCase(
            uid = uid,
            onSuccess = { user ->
                DataUtil.tripUser = user
                Log.e("DataStore","User found successfully with uid : $uid")
            },
            onFailure = {
                Log.e("DataStore Error","No user found")
            }
        )
    }
}