package com.example.travelapp.screens.register

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.TripUserEntity
import com.example.domain.use_cases.auth.AuthUseCases
import com.example.domain.use_cases.user.UserUseCases
import com.example.travelapp.utils.areRegisterFieldsValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val mAuthUseCases: AuthUseCases,
    private val mUserUseCases: UserUseCases
): ViewModel() {
    private val _authStateFlow = MutableStateFlow<Resource<Unit>>(Resource.Unspecified())
    val authStateFlow = _authStateFlow.asStateFlow()

    private val _profileImageStateFlow = MutableStateFlow<Resource<Bitmap?>>(Resource.Unspecified())
    val profileImageStateFlow = _profileImageStateFlow.asStateFlow()

    private val _navigationSharedFlow = MutableSharedFlow<Boolean>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    private val registerErrorState = mutableStateOf("")

    val imageUriState = mutableStateOf<Uri?>(null)

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
            is RegisterScreenEvents.OnSubmitButtonClick -> {
                register()
            }

            is RegisterScreenEvents.OnErrorDismiss -> {
                clearAuthStateFlow()
            }

            is RegisterScreenEvents.OnChooseImageClick ->{
                displayChosenImage(event.contentResolver)
            }
        }
    }

    private fun displayChosenImage(
        contentResolver: ContentResolver,
    ){
        if (imageUriState.value == null) {
            viewModelScope.launch {
                _profileImageStateFlow.emit(Resource.Failure("No Image Chosen"))
            }
            return
        }

        viewModelScope.launch {
            _profileImageStateFlow.emit(Resource.Loading())
            try {
                val imageInputStream = contentResolver.openInputStream(imageUriState.value!!)
                val bitmap = imageInputStream.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
                _profileImageStateFlow.emit(Resource.Success(bitmap))
            } catch (e: Exception) {
                _profileImageStateFlow.emit(Resource.Failure("Failed to load image : ${e.message}"))
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
                            saveImage(
                                fileName = "user_${UUID.randomUUID()}.jpg",
                                uri = imageUriState.value,
                                onSuccess = { imagePath,imageUrlParam ->
                                    val user = TripUserEntity(
                                        email = emailState.value,
                                        name = nameState.value,
                                        phoneNumber = phoneNoState.value,
                                        imageURL = imageUrlParam,
                                        imagePath = imagePath
                                    )
                                    Log.e("Imgur Save" , "Imaged Saved")

                                    saveUser(
                                        user = user,
                                        onSuccess = {
                                            viewModelScope.launch {
                                                _authStateFlow.emit(Resource.Success(Unit))
                                                _navigationSharedFlow.emit(true)
                                            }
                                        },
                                        onFailure = {
                                            viewModelScope.launch {
                                                _authStateFlow.emit(Resource.Failure(it.message))
                                            }
                                        }
                                    )

                                    DataUtil.tripUser = user

                                    Log.e("FIB Auth ViewModel" , "Registered successfully")
                                },
                                onFailure = {
                                    viewModelScope.launch {
                                        _authStateFlow.emit(Resource.Failure(it.message))
                                    }
                                }
                            )
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

    private fun saveUser(
        user : TripUserEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        mUserUseCases.saveUserUseCase(user, onSuccess, onFailure)
    }
    private fun saveImage(
        fileName : String,
        uri : Uri?,
        onSuccess : (String? ,String) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mUserUseCases.saveImageUseCase(fileName, uri, onSuccess, onFailure)
    }
}