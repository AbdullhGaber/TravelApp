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
) : ViewModel() {}