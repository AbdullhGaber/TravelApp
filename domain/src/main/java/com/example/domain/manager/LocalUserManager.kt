package com.example.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveUserUID(uid : String)
    suspend fun getUserUID() : Flow<String?>
}