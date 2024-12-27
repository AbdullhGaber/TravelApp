package com.example.data.manager

import com.example.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MangerModule {
    @Binds
    @Singleton
    abstract fun provideLocalUserManager(localUserManagerImpl: LocalUserManagerImpl) : LocalUserManager
}