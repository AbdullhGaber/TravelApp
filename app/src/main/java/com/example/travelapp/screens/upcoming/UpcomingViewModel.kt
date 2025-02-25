package com.example.travelapp.screens.upcoming

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.TripEntity
import com.example.domain.use_cases.trip.TripUseCases
import com.example.travelapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val mTripUseCases: TripUseCases,
    @ApplicationContext private val mContext: Context
) : ViewModel() {
    private val _tripsStateFlow = MutableStateFlow<Resource<List<TripEntity>?>>(Resource.Unspecified())
    val tripStateFlow = _tripsStateFlow.asStateFlow()

    private val _sharedTripStateFlow = MutableSharedFlow<String>(replay = 0)
    val sharedTripStateFlow = _sharedTripStateFlow.asSharedFlow()

    private val _lastDeletedTripStateFlow = MutableStateFlow(TripEntity())
    val lastDeletedTripStateFlow = _lastDeletedTripStateFlow.asStateFlow()

    init {
        getTrips()
    }
    fun onEvent(event: UpcomingEvents){
        when(event){
            is UpcomingEvents.OnTripReminderDialogCancelClick -> Unit

            is UpcomingEvents.OnTripCardDeleteClick -> {
                deleteTrip(event.trip)
            }

            is UpcomingEvents.OnUndoDeleteClick -> {
                undoDeleteTrip(event.trip)
            }
        }
    }

    private fun undoDeleteTrip(trip : TripEntity){
        mTripUseCases.addTripUseCase(
            trip = trip,
            onSuccess = {
                viewModelScope.launch {
                    _sharedTripStateFlow.emit(mContext.getString(R.string.trip_undo_successfully))
                }
            },
            onFailure = {
                viewModelScope.launch {
                    _sharedTripStateFlow.emit(mContext.getString(R.string.something_went_wrong) + " : ${it.message}")
                }
            }
        )
    }

    private fun deleteTrip(trip: TripEntity){
        mTripUseCases.deleteTripUseCase(
            tripId = trip.id!!,
            uid = trip.uid,
            onSuccess = {
                viewModelScope.launch {
                    _sharedTripStateFlow.emit(mContext.getString(R.string.trip_deleted_successfully))
                    _lastDeletedTripStateFlow.emit(trip)
                }
            },
            onFailure = {
                viewModelScope.launch {
                    _sharedTripStateFlow.emit(mContext.getString(R.string.something_went_wrong) + " : ${it.message}")
                }
            }
        )
    }
    private fun getTrips(){
        val uid = DataUtil.tripUser?.uid ?: ""

        viewModelScope.launch {
            _tripsStateFlow.emit(
                Resource.Loading()
            )
        }

        mTripUseCases.getTripUseCase(
            uid = uid,
            onSuccess = { trips ->
                viewModelScope.launch {
                    _tripsStateFlow.emit(
                        Resource.Success(trips)
                    )
                    Log.e("FIB FireStore ViewModel","Trips retrieved successfully")
                }
            },
            onFailure = {
                viewModelScope.launch {
                    _tripsStateFlow.emit(
                        Resource.Failure(it.message)
                    )
                }
            }
        )
    }
}