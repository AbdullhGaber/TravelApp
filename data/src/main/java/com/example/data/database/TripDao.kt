package com.example.data.database

import androidx.annotation.IntRange
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TripModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("UPDATE trips SET hasFirstTripTimeCome = :value WHERE id = :id")
    suspend fun updateHasFirstTripTimeCome(id : String, @IntRange(0,1) value : Int)

    @Query("UPDATE trips SET hasSecondTripTimeCome = :value WHERE id = :id")
    suspend fun updateHasSecondTripTimeCome(id : String, @IntRange(0,1) value : Int)

    @Query("SELECT * FROM trips")
    fun getAllTrips() : Flow<List<TripModel>>

    @Query("SELECT * FROM trips WHERE hasFirstTripTimeCome = 1 OR hasSecondTripTimeCome = 1")
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