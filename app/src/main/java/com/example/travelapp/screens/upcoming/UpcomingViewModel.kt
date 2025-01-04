package com.example.travelapp.screens.upcoming

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.TripEntity
import com.example.domain.use_cases.trip.TripUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val mTripUseCases: TripUseCases
) : ViewModel() {
    private val _tripsStateFlow = MutableStateFlow<Resource<List<TripEntity>?>>(Resource.Unspecified())
    val tripStateFlow = _tripsStateFlow.asStateFlow()

    init {
        getTrips()
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