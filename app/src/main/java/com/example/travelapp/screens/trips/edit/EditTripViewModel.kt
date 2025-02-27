package com.example.travelapp.screens.trips.edit

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.textToLocalDate
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.TripEntity
import com.example.domain.use_cases.trip.TripUseCases
import com.example.travelapp.R
import com.example.travelapp.utils.AddTripValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTripViewModel @Inject constructor(
    private val mTripUseCases: TripUseCases,
    @ApplicationContext private val mContext: Context
): ViewModel() {
    val addTripValidator = AddTripValidator(mContext)

    val tripState = mutableStateOf<Resource<TripEntity>>(Resource.Unspecified())

    private val _editTripSharedFlow = MutableSharedFlow<String>()
    val editTripSharedFlow = _editTripSharedFlow.asSharedFlow()

    val tripStartPState by lazy{mutableStateOf(tripState.value.data?.startDestination!!)}
    val tripStartPErrorState = mutableStateOf("")

    val tripEndPState by lazy { mutableStateOf(tripState.value.data?.endDestination!!) }
    val tripEndPErrorState = mutableStateOf("")

    val tripNameState by lazy { mutableStateOf(tripState.value.data?.name!!) }
    val tripNameErrorState = mutableStateOf("")

    val showSingleTripDatePickerDialog  = mutableStateOf(false)
    val selectedSingleTripDate by lazy { mutableStateOf(tripState.value.data?.date) }
    val selectedSingleDateErrorState = mutableStateOf("")

    val showSingleTripTimePickerDialog = mutableStateOf(false)
    val selectedSingleTripTime by lazy { mutableStateOf(tripState.value.data?.time) }
    val selectedSingleTimeErrorState = mutableStateOf("")

    val roundTripShowDatePickerDialog  = mutableStateOf(false)
    val roundTripSelectedDate by lazy { mutableStateOf(tripState.value.data?.returnDate) }
    val roundTripSelectedDateErrorState = mutableStateOf("")

    val roundTripShowTimePickerDialog = mutableStateOf(false)
    val roundTripSelectedTime by lazy { mutableStateOf(tripState.value.data?.returnTime) }
    val roundTripSelectedTimeErrorState = mutableStateOf("")

    val isExpanded = mutableStateOf(false)
    val isRoundTrip = mutableStateOf(false)

     fun onEvent(event : EditTripEvents){
         when(event){
             is EditTripEvents.OnEditButtonClick -> {
                 updateTrip()
             }

             is EditTripEvents.OnErrorDialogDismiss -> {
                 clearTripState()
             }
         }
     }
     fun getTripById(tripId : String, uid : String){
        tripState.value = Resource.Loading()

        mTripUseCases.getTripByIdUseCase(
            id = tripId,
            uid = uid,
            onSuccess = {
                tripState.value = Resource.Success(it)
            },
            onFailure = {
                tripState.value = Resource.Failure(it.message)
            }
        )
    }

    private fun clearTripState() {tripState.value = Resource.Unspecified()}

    private fun updateTrip(){
        if(areFieldsValid()){
            val tripId = tripState.value.data?.id
            tripState.value = Resource.Loading()
            Log.e("Edit ViewModel","entered if areFieldsValid")
            if(!isRoundTrip.value){
                roundTripSelectedDate.value = null
                roundTripSelectedTime.value = null
            }

            val trip = TripEntity(
                id = tripId,
                name = tripNameState.value,
                uid =  DataUtil.tripUser?.uid ?: "",
                status = TripEntity.UPCOMING,
                type = if(isRoundTrip.value) TripEntity.ROUND_DIRECTION_TRIP else TripEntity.ONE_DIRECTION_TRIP ,
                startDestination = tripStartPState.value,
                endDestination = tripEndPState.value,
                date = selectedSingleTripDate.value!!,
                time = selectedSingleTripTime.value!!,
                returnDate = roundTripSelectedDate.value,
                returnTime = roundTripSelectedTime.value
            )

            mTripUseCases.updateTripUseCase(
                trip = trip,
                onSuccess = {
                    viewModelScope.launch {
                        _editTripSharedFlow.emit("Your Trip Updated Successfully")
                    }

                    clearTripState()

                    mTripUseCases.scheduleTripNotificationUseCase(trip)
                },
                onFailure = {
                    viewModelScope.launch {
                        _editTripSharedFlow.emit("Something went wrong ${it.message}")
                        tripState.value = Resource.Failure(it.message)
                    }
                }
            )
        }
    }

    private fun areFieldsValid() : Boolean{
        val startLocalDate = textToLocalDate(selectedSingleTripDate.value)
        val startMillis = localDateToMillis(startLocalDate)

        val endLocalDate = textToLocalDate(roundTripSelectedDate.value)
        val endMillis = localDateToMillis(endLocalDate)

        return addTripValidator.areAddTripFieldsValid(
            tripName = tripNameState.value,
            tripNameErrorState = tripNameErrorState,
            startPoint = tripStartPState.value,
            startPointErrorState = tripStartPErrorState,
            endPoint = tripEndPState.value,
            endPointErrorState = tripEndPErrorState,
            startDateMillis = startMillis,
            dateErrorState = selectedSingleDateErrorState,
            startTime = selectedSingleTripTime.value,
            startTimeErrorState = selectedSingleTimeErrorState,
            endDateMillis = endMillis,
            isRoundTrip = isRoundTrip.value,
            roundTripDateErrorState = roundTripSelectedDateErrorState,
            endTripTime = roundTripSelectedTime.value,
            endTripTimeErrorState = roundTripSelectedTimeErrorState,
        )
    }

    fun isNoErrors() : Boolean{
        return if((isRoundTrip.value)){
            ((tripStartPState.value.isNotEmpty() && tripStartPErrorState.value.isEmpty())
                    && (tripEndPState.value.isNotEmpty() && tripEndPErrorState.value.isEmpty())
                    && (tripNameState.value.isNotEmpty() && tripNameErrorState.value.isEmpty())
                    && (selectedSingleTripDate.value != null && selectedSingleDateErrorState.value.isEmpty())
                    && (selectedSingleTripTime.value != null && selectedSingleTimeErrorState.value.isEmpty())
                    && (roundTripSelectedDate.value != null && roundTripSelectedDateErrorState.value.isEmpty())
                    && (roundTripSelectedTime.value != null && roundTripSelectedTimeErrorState.value.isEmpty()))
        }else{
            ((tripStartPState.value.isNotEmpty() && tripStartPErrorState.value.isEmpty())
                    && (tripEndPState.value.isNotEmpty() && tripEndPErrorState.value.isEmpty())
                    && (tripNameState.value.isNotEmpty() && tripNameErrorState.value.isEmpty())
                    && (selectedSingleTripDate.value != null && selectedSingleDateErrorState.value.isEmpty())
                    && (selectedSingleTripTime.value != null && selectedSingleTimeErrorState.value.isEmpty()))
        }
    }
}