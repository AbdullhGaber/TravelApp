package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class TripUserModel(
    @PrimaryKey val uid: String,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val imageURL: String,
    val imagePath: String? = ""
)