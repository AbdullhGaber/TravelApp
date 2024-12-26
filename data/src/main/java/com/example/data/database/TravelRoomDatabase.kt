package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.TripUserModel
import com.example.domain.entity.TripUserEntity

@Database(entities = [TripUserModel::class], version = 1, exportSchema = false)
abstract class TravelRoomDatabase : RoomDatabase(){
    abstract val userDao : UserDao
}