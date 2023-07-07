package com.hikizan.hikigram.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.hikizan.hikigram.utils.constants.BundleKeys.KEY_USER_PREFERENCE_LOGIN_NAME
import com.hikizan.hikigram.utils.constants.BundleKeys.KEY_USER_PREFERENCE_LOGIN_TOKEN
import com.hikizan.hikigram.utils.constants.BundleKeys.KEY_USER_PREFERENCE_LOGIN_STATE
import com.hikizan.hikigram.utils.ext.emptyString
import com.hikizan.hikigram.utils.ext.orEmptyString
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    suspend fun saveLoginState(isLogin: Boolean, token: String = emptyString, userName: String = emptyString) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_USER_PREFERENCE_LOGIN_STATE] = isLogin
            if (isLogin) {
                mutablePreferences[KEY_USER_PREFERENCE_LOGIN_TOKEN] = token.orEmptyString()
                mutablePreferences[KEY_USER_PREFERENCE_LOGIN_NAME] = userName.orEmptyString()
            } else {
                mutablePreferences.remove(KEY_USER_PREFERENCE_LOGIN_TOKEN)
            }
        }
    }

    fun getLoginToken() =
        dataStore.data.map { value: Preferences ->  
            value[KEY_USER_PREFERENCE_LOGIN_TOKEN].orEmptyString()
        }

    fun getLoginState() =
        dataStore.data.map { value: Preferences ->
            value[KEY_USER_PREFERENCE_LOGIN_STATE] ?: false
        }

    fun getLoginName() =
        dataStore.data.map { value: Preferences ->
            value[KEY_USER_PREFERENCE_LOGIN_NAME].orEmptyString()
        }

}