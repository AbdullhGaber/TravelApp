package com.example.travelapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.manager.LocalUserManager
import com.example.travelapp.screens.nav_graph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mLocalUserManager: LocalUserManager
) : ViewModel() {
    var startDestination = mutableStateOf(Route.AuthNavigation.route)
        private set

    init{
        setStartDestination()
    }

    private fun setStartDestination(){
        viewModelScope.launch {
            mLocalUserManager.getUserUID().collect{ uid ->
                uid?.let{startDestination.value = Route.HomeNavigation.route}
            }
        }
    }


    private val shouldShowTripReminderDialog = mutableStateOf(false)
    private val tripNameState = mutableStateOf("")
    private val tripStartDesState = mutableStateOf("")
    private val tripEndDesState = mutableStateOf("")

    fun getShouldShowTripReminderDialog() = shouldShowTripReminderDialog
    fun getTripNameState() = tripNameState
    fun getTripStartDesState() = tripStartDesState
    fun getTripEndDesState() = tripEndDesState

    fun setTripData(
        name : String,
        startDes : String,
        endDes : String
    ){
        tripNameState.value = name
        tripStartDesState.value = startDes
        tripEndDesState.value = endDes
    }

    fun showTripReminderDialog() {
        shouldShowTripReminderDialog.value = true
    }

    fun dismissTripReminderDialog() {
        shouldShowTripReminderDialog.value = false
    }
}