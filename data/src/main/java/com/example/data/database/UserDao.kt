package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.TripUserModel
import com.example.domain.entity.TripUserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: TripUserModel)

    @Query("SELECT * FROM `users` WHERE `uid` = :uid")
    suspend fun getUserById(uid: String): TripUserModel?
}
