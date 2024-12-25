package com.example.travelapp.screens.navigator.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.travelapp.R
import com.example.travelapp.screens.nav_graph.Route

data class NavItems(
    @StringRes val nameStringRes : Int,
    @DrawableRes val icon : Int,
    val route : String
){
    companion object{
        val navItems = listOf(
            NavItems(
                nameStringRes = R.string.upcoming,
                icon = R.drawable.arrow_up_ic,
                route = Route.UpComingScreen.route
            ),

            NavItems(
                nameStringRes = R.string.history,
                icon = R.drawable.history_ic,
                route = Route.HistoryScreen.route
            ),

            NavItems(
                nameStringRes = R.string.history_map,
                icon = R.drawable.map_ic,
                route = Route.HistoryMapScreen.route
            ),
        )
    }
}

