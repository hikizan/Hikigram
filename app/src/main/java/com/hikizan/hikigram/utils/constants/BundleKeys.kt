package com.hikizan.hikigram.utils.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object BundleKeys {
    const val KEY_SUCCESS_PAGE_TITLE: String = "key_success_page_title"
    const val KEY_SUCCESS_PAGE_DESC: String = "key_success_page_desc"
    const val KEY_SUCCESS_PAGE_ANIMATION: String = "key_success_page_animation"
    const val KEY_SUCCESS_PAGE_POSITIVE_BUTTON: String = "key_success_page_positive_button"

    const val KEY_STORY_ID: String = "key_story_id"

    const val USER_PREFERENCE_SETTINGS = "user_preference_settings"
    val KEY_USER_PREFERENCE_LOGIN_NAME = stringPreferencesKey("name")
    val KEY_USER_PREFERENCE_LOGIN_TOKEN = stringPreferencesKey("login_token")
    val KEY_USER_PREFERENCE_LOGIN_STATE = booleanPreferencesKey("login_state")
}