package com.example.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideTravelRoomDatabase(
        @ApplicationContext context : Context
    ) : TravelRoomDatabase{
        return Room.databaseBuilder(
            context,
            TravelRoomDatabase::class.java,
            "travel_db"
        ).build()
    }

    @Provides
    fun provideUserDao(
        travelDB : TravelRoomDatabase
    ): UserDao{
        return travelDB.userDao
    }
}