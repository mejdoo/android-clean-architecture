package com.mejdoo.clean

import android.app.Application
import com.mejdoo.clean.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * This is the application class
 */
class CleanApplication : Application() {

    override fun onCreate() {
        super.onCreate()


        // Initialization of Dependency Injection library
        startKoin {
            androidContext(this@CleanApplication)

            modules(
                viewModelModule,
                useCaseModule,
                repositoryModule,
                dataSourceModule,
                networkModule,
                cacheModule,
                mapperModule
            )
        }

    }

}