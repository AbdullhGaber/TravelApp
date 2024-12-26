package com.example.travelapp.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.use_cases.auth.AuthUseCases
import com.example.domain.use_cases.user.UserUseCases
import com.example.travelapp.utils.isEmailValid
import com.example.travelapp.utils.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mAuthUseCases: AuthUseCases,
    private val mUserUseCases: UserUseCases
) : ViewModel() {
    private val _authStateFlow = MutableStateFlow<Resource<Unit>>(Resource.Unspecified())
    val authStateFlow = _authStateFlow.asStateFlow()

    private val _navigationSharedFlow = MutableSharedFlow<Boolean>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    private val loginErrorState = mutableStateOf("")

    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf("")

    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf("")
    val isPasswordVisibleState = mutableStateOf(false)

    fun onEvent(event : LoginScreenEvents){
        when(event){
            is LoginScreenEvents.Login -> {
                login()
            }

            is LoginScreenEvents.ClearAuthFlowState -> {
                clearAuthFlowState()
            }
        }
    }

    private fun clearAuthFlowState(){
        _authStateFlow.value = Resource.Unspecified()
    }

    private fun login(){
        viewModelScope.launch {
            _authStateFlow.emit(Resource.Loading())
        }

        if(areFieldsValid()){
            viewModelScope.launch {
                mAuthUseCases.loginUseCase(
                    email = emailState.value,
                    password = passwordState.value,
                    onSuccess = { uid ->
                        viewModelScope.launch {
                            _authStateFlow.emit(Resource.Success(Unit))
                            _navigationSharedFlow.emit(true)
                            uid?.let{getUser(it)}
                            Log.e("FIB Auth ViewModel" , "Logged successfully")
                        }
                    },
                    onFailure = {
                        viewModelScope.launch {
                            _authStateFlow.emit(Resource.Failure(message = it.message))
                            loginErrorState.value  = it.message.toString()
                            Log.e("FIB Auth ViewModel" , "Error : ${it.message}")
                        }
                    }
                )
            }
        }
    }

    private fun getUser(uid : String){
        mUserUseCases.getUserUseCase(
            uid = uid,
            onSuccess = { user ->
                DataUtil.tripUser = user
            },
            onFailure = {
                Log.e("FIB Auth ViewModel" , "Error : ${it.message}")
            }
        )
    }

    private fun areFieldsValid() : Boolean{
        return isEmailValid(
            email = emailState.value,
            emailError = emailErrorState
        ) && isPasswordValid(
            password = passwordState.value,
            passwordError = passwordErrorState
        )
    }

    fun isNoErrors() : Boolean{
        return (emailState.value.isNotEmpty() && emailErrorState.value.isEmpty())
                && (passwordState.value.isNotEmpty() && passwordErrorState.value.isEmpty())

    }
}