package com.hikizan.hikigram.base

import android.app.Application
import com.hikizan.hikigram.BuildConfig
import com.hikizan.hikigram.di.dataStoreModule
import com.hikizan.hikigram.di.fetureModules.authRepositoryModule
import com.hikizan.hikigram.di.fetureModules.authUseCaseModule
import com.hikizan.hikigram.di.fetureModules.authViewModelModule
import com.hikizan.hikigram.di.fetureModules.storyRepositoryModule
import com.hikizan.hikigram.di.fetureModules.storyUseCaseModule
import com.hikizan.hikigram.di.fetureModules.storyViewModelModule
import com.hikizan.hikigram.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class HikigramApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@HikigramApplication)
            modules(
                listOf(
                    networkModule,
                    dataStoreModule,

                    /* Auth */
                    authRepositoryModule,
                    authUseCaseModule,
                    authViewModelModule,

                    /* Story */
                    storyRepositoryModule,
                    storyUseCaseModule,
                    storyViewModelModule
                )
            )
        }
    }
}