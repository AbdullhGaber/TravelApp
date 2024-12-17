package com.example.travelapp.screens.register

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.Resource
import com.example.domain.use_cases.auth.AuthUseCases
import com.example.travelapp.utils.areRegisterFieldsValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val mAuthUseCases: AuthUseCases
): ViewModel() {
    private val _authStateFlow = MutableStateFlow<Resource<Unit>>(Resource.Unspecified())
    val authStateFlow = _authStateFlow.asStateFlow()

    private val _navigationSharedFlow = MutableSharedFlow<Boolean>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    private val registerErrorState = mutableStateOf("")

    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")

    val nameState = mutableStateOf("")
    val nameErrorState = mutableStateOf("")

    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")

    val rePasswordState = mutableStateOf("")
    val rePasswordErrorState = mutableStateOf("")

    val isPasswordVisibleState = mutableStateOf(false)
    val isRePasswordVisibleState = mutableStateOf(false)

    val phoneNoState = mutableStateOf("")
    val phoneNoErrorState = mutableStateOf("")

    fun onEvent(event : RegisterScreenEvents){
        when(event){
            is RegisterScreenEvents.Register -> {
                register()
            }

            is RegisterScreenEvents.ClearAuthFlowState -> {
                clearAuthStateFlow()
            }
        }
    }

    private fun clearAuthStateFlow(){
        _authStateFlow.value = Resource.Unspecified()
    }

    private fun register(){
        viewModelScope.launch {
            _authStateFlow.emit(Resource.Loading())
        }

        if(areFieldsValid()){
            viewModelScope.launch {
                mAuthUseCases.registerUseCase(
                    email = emailState.value,
                    password = passwordState.value,
                    onSuccess = {
                        viewModelScope.launch {
                            _authStateFlow.emit(Resource.Success(Unit))
                            _navigationSharedFlow.emit(true)
                            Log.e("FIB Auth ViewModel" , "Registered successfully")
                        }
                    },
                    onFailure = {
                        viewModelScope.launch {
                            _authStateFlow.emit(Resource.Failure(message = it.message))
                            registerErrorState.value  = it.message.toString()
                            Log.e("FIB Auth ViewModel" , "Error : ${it.message}")
                        }
                    }
                )
            }
        }
    }

    private fun areFieldsValid() : Boolean{
        return areRegisterFieldsValid(
            emailState.value,
            emailErrorState,
            nameState.value,
            nameErrorState,
            phoneNoState.value,
            phoneNoErrorState,
            passwordState.value,
            passwordErrorState,
            rePasswordState.value,
            rePasswordErrorState
        )
    }

    fun isNoErrors() : Boolean{
        return (emailState.value.isNotEmpty() && emailErrorState.value.isEmpty())
                && (nameState.value.isNotEmpty() && nameErrorState.value.isEmpty())
                && (phoneNoState.value.isNotEmpty() && phoneNoErrorState.value.isEmpty())
                && (passwordState.value.isNotEmpty() && passwordErrorState.value.isEmpty())
                && (rePasswordState.value.isNotEmpty() && rePasswordErrorState.value.isEmpty())
    }
}