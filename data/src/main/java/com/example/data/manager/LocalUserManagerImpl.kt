package com.example.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.manager.LocalUserManagerImpl.Companion.PREFERENCES_NAME
import com.example.data.manager.LocalUserManagerImpl.Companion.USER_UID
import com.example.data.manager.PreferencesKey.USER_UID_KEY
import com.example.domain.manager.LocalUserManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor(
    @ApplicationContext private val mContext: Context
) : LocalUserManager {
    override suspend fun saveUserUID(uid: String) {
        mContext.dataStore.edit { preferences ->
            preferences[USER_UID_KEY] = uid
        }
    }

    override suspend fun getUserUID(): Flow<String?> {
        return mContext.dataStore.data.map { preferences ->
            preferences[USER_UID_KEY]
        }
    }

    companion object {
        const val PREFERENCES_NAME = "user_pref"
        const val USER_UID = "user_uid"
    }
}

val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

private object PreferencesKey{
    val USER_UID_KEY =  stringPreferencesKey(name = USER_UID)
}

