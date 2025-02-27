package com.example.data.database

import androidx.annotation.IntRange
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TripModel
import com.example.domain.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("UPDATE trips SET hasTimeCome = :value WHERE id = :id")
    suspend fun updateHasTimeCome(id : String, @IntRange(0,1) value : Int)

    @Query("SELECT * FROM trips")
    fun getAllTrips() : Flow<List<TripModel>>

    @Query("SELECT * FROM trips WHERE hasTimeCome = 1")
    fun getScheduledTrips() : Flow<List<TripModel>>

    @Insert
    suspend fun addTrip(tripModel: TripModel)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: String)

    @Query("SELECT * FROM trips WHERE id = :tripId")
    fun getTripById(tripId : String) : Flow<TripModel?>

    @Update
    suspend fun updateTrip(tripModel: TripModel)

}