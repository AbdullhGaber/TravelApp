package com.example.travelapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.domain.manager.LocalUserManager
import com.example.domain.use_cases.user.UserUseCases
import com.example.travelapp.screens.nav_graph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mLocalUserManager: LocalUserManager,
    private val mUserUseCases: UserUseCases,
) : ViewModel() {
    var startDestination = mutableStateOf(Route.AuthNavigation.route)
        private set

    var shouldSplashScreenOn = mutableStateOf(true)
        private set

    init{
        setStartDestination()
    }
    private fun setStartDestination(){
        viewModelScope.launch {
            mLocalUserManager.getUserUID().collect{ uid ->
                if(uid == null) shouldSplashScreenOn.value = false
                Log.e("MainViewModel","collected UID = $uid")
                uid?.let{
                    getUser(uid)
                }
            }
        }
    }
    private fun getUser(uid : String){
        mUserUseCases.getUserUseCase(
            uid = uid,
            onSuccess = { user ->
                DataUtil.tripUser = user
                Log.e("DataStore","User found successfully with uid : $uid")
                startDestination.value = Route.HomeNavigation.route
                viewModelScope.launch {
                    delay(2000)
                    shouldSplashScreenOn.value = false
                }
            },
            onFailure = {
                Log.e("DataStore Error","No user found")
            }
        )
    }
}