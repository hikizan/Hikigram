package com.hikizan.hikigram.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.hikizan.hikigram.data.local.datastore.UserPreference
import com.hikizan.hikigram.data.remote.network.ApiClient
import com.hikizan.hikigram.utils.constants.BundleKeys.USER_PREFERENCE_SETTINGS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .connectTimeout(120, SECONDS)
            .readTimeout(120, SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiClient::class.java)
    }
}

private val Context.datastore by preferencesDataStore(USER_PREFERENCE_SETTINGS)

val dataStoreModule = module {
    single {
        UserPreference(androidContext().datastore)
    }
}