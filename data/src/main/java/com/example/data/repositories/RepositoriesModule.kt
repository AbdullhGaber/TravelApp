package com.example.data.repositories

import com.example.data.data_soruce.auth.AuthRemoteDataSourceImpl
import com.example.data.data_soruce.user.UserOfflineDataSourceImpl
import com.example.data.data_soruce.user.UserRemoteDataSourceImpl
import com.example.data.repositories.auth.AuthRepositoryImpl
import com.example.data.repositories.user.UserRepositoryImpl
import com.example.domain.repositories.auth.AuthRemoteDataSource
import com.example.domain.repositories.auth.AuthRepository
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
}