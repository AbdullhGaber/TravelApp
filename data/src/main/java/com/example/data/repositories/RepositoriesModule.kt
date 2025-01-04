package com.example.data.repositories

import com.example.data.data_soruce.auth.AuthRemoteDataSourceImpl
import com.example.data.data_soruce.trip.TripOfflineDataSourceImpl
import com.example.data.data_soruce.trip.TripRemoteDataSourceImpl
import com.example.data.data_soruce.user.UserOfflineDataSourceImpl
import com.example.data.data_soruce.user.UserRemoteDataSourceImpl
import com.example.data.repositories.auth.AuthRepositoryImpl
import com.example.data.repositories.trip.TripRepositoryImpl
import com.example.data.repositories.user.UserRepositoryImpl
import com.example.domain.repositories.auth.AuthRemoteDataSource
import com.example.domain.repositories.auth.AuthRepository
import com.example.domain.repositories.trip.TripOfflineDataSource
import com.example.domain.repositories.trip.TripRemoteDataSource
import com.example.domain.repositories.trip.TripRepository
import com.example.domain.repositories.user.UserOfflineDataSource
import com.example.domain.repositories.user.UserRemoteDataSource
import com.example.domain.repositories.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl) : AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl) : UserRemoteDataSource


    @Binds
    @Singleton
    abstract fun provideUserOfflineDataSource(userOfflineDataSourceImpl: UserOfflineDataSourceImpl) : UserOfflineDataSource

    @Binds
    @Singleton
    abstract fun provideTripRemoteDataSource(tripRemoteDataSourceImpl: TripRemoteDataSourceImpl) : TripRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideTripOfflineDataSource(tripOfflineDataSourceImpl: TripOfflineDataSourceImpl) : TripOfflineDataSource

    @Binds
    @Singleton
    abstract fun provideTripRepository(tripRepositoryImpl: TripRepositoryImpl) : TripRepository
}