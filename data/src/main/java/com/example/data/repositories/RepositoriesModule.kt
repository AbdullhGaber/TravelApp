package com.example.data.repositories

import com.example.data.data_soruce.auth.AuthRemoteDataSourceImpl
import com.example.data.repositories.auth.AuthRepositoryImpl
import com.example.domain.repositories.auth.AuthRemoteDataSource
import com.example.domain.repositories.auth.AuthRepository
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
}