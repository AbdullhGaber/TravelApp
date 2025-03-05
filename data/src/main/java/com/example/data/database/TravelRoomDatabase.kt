package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.NoteModel
import com.example.data.model.TripModel
import com.example.data.model.TripUserModel
import com.example.domain.entity.TripUserEntity
@TypeConverters(Converters::class)
@Database(entities = [TripUserModel::class,TripModel::class,NoteModel::class], version = 4, exportSchema = false)
abstract class TravelRoomDatabase : RoomDatabase(){
    abstract val userDao : UserDao
    abstract val tripDao : TripDao
    abstract val noteDao : NoteDao
}