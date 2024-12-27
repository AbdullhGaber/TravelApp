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

    init{
        setStartDestination()
    }

    var startDestination = mutableStateOf(Route.AuthNavigation.route)
        private set

    private fun setStartDestination(){
        viewModelScope.launch {
            mLocalUserManager.getUserUID().collect{ uid ->
                uid?.let{startDestination.value = Route.HomeNavigation.route}
            }
        }
    }
}