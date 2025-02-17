package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.TripModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("UPDATE trips SET hasTimeCome = 1 WHERE id = :id")
    suspend fun setHasTimeComeToTrue(id : String)

    @Query("SELECT * FROM trips")
    fun getAllTrips() : Flow<List<TripModel>>

    @Query("SELECT * FROM trips WHERE hasTimeCome = 1")
    fun getScheduledTrips() : Flow<List<TripModel>>

    @Insert
    suspend fun addTrip(tripModel: TripModel)

}