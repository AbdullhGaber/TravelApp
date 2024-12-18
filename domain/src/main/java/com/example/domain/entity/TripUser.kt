package com.example.domain.entity

data class TripUser(
    val email : String,
    val name : String,
    val phoneNumber : String,
    val imageURI : String
){
    companion object{
        const val USER_COLLECTION = "users"
    }
}
