package com.example.travelapp

import android.util.Log
import androidx.annotation.IntRange
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.TripEntity
import com.example.domain.manager.LocalUserManager
import com.example.domain.use_cases.trip.TripUseCases
import com.example.domain.use_cases.user.UserUseCases
import com.example.travelapp.screens.nav_graph.Route
import com.example.travelapp.screens.upcoming.UpcomingEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mLocalUserManager: LocalUserManager,
    private val mUserUseCases: UserUseCases,
    private val mTripUseCases: TripUseCases,
) : ViewModel() {
    var startDestination = mutableStateOf(Route.AuthNavigation.route)
        private set

    private val _scheduledTripsStateFlow = MutableStateFlow<Resource<List<TripEntity>?>>(Resource.Unspecified())
    val scheduledTripsStateFlow = _scheduledTripsStateFlow.asStateFlow()

    init{
        setStartDestination()
        getScheduledTrips()
    }

    fun onEvent(event : UpcomingEvents){
        when(event){
            is UpcomingEvents.OnTripReminderDialogCancelClick -> {
                dismissTripReminderDialog()
                clearScheduledTripFlowState()
                updateHasTimeComeInTrip(event.id, event.value)
            }

            is UpcomingEvents.OnTripCardDeleteClick -> Unit

            is UpcomingEvents.OnUndoDeleteClick -> Unit
        }
    }
    private fun updateHasTimeComeInTrip(id : String, @IntRange(0,1) value : Int){
        viewModelScope.launch {
            try{
                mTripUseCases.updateTripHasTimeComeUseCase(id,value)
            }catch(e : Exception){
                Log.e("MainViewModel Error", e.message.toString())
            }
        }
    }
    private fun getScheduledTrips(){
        viewModelScope.launch {
            _scheduledTripsStateFlow.emit(Resource.Loading())
        }

        viewModelScope.launch {
            try{
                mTripUseCases.getScheduledTrips().collect{trips ->
                    _scheduledTripsStateFlow.emit(Resource.Success(trips))
                }
            }catch (e : Exception){
                viewModelScope.launch {
                    _scheduledTripsStateFlow.emit(Resource.Failure(e.message))
                }
            }
        }
    }

    private fun setStartDestination(){
        viewModelScope.launch {
            mLocalUserManager.getUserUID().collect{ uid ->
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
            },
            onFailure = {
                Log.e("DataStore Error","No user found")
            }
        )
    }

    private val shouldShowTripReminderDialog = mutableStateOf(false)

    fun setTripDataForNotification(
        id : String,
        name : String,
        startDes : String,
        endDes : String
    ){
        val trip = TripEntity(id = id, name = name, startDestination = startDes, endDestination = endDes)
        viewModelScope.launch {
           _scheduledTripsStateFlow.emit(Resource.Success(listOf(trip)))
        }
    }

    fun getShouldShowTripReminderDialog() = shouldShowTripReminderDialog.value

    private fun clearScheduledTripFlowState(){
        viewModelScope.launch {
            _scheduledTripsStateFlow.emit(Resource.Unspecified())
        }
    }
    fun showTripReminderDialog() {
        shouldShowTripReminderDialog.value = true
    }

    fun dismissTripReminderDialog() {
        shouldShowTripReminderDialog.value = false
    }
}