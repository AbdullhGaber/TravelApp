package com.example.domain.entity


data class TripUserEntity(
    var uid : String = "-1",
    val email : String = "",
    val name : String = "",
    val phoneNumber : String = "",
    val imageURL : String = "",
    val imagePath : String? = "",
){
    companion object{
        const val USER_COLLECTION = "users"
    }
}
