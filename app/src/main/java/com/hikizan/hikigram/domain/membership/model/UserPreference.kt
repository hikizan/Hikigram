package com.hikizan.hikigram.domain.membership.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hikizan.hikigram.utils.ext.orEmptyString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {
    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveUser(user: UserAuthModelDummy) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
            preferences[STATE_KEY] = user.isLogin
        }
    }

    fun getUser(): Flow<UserAuthModelDummy> {
        return dataStore.data.map { preferences ->
            UserAuthModelDummy(
                name = preferences[NAME_KEY].orEmptyString(),
                email = preferences[EMAIL_KEY].orEmptyString(),
                password = preferences[PASSWORD_KEY].orEmptyString(),
                isLogin = preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun login() {
        dataStore.edit { prefences ->
            prefences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }
}