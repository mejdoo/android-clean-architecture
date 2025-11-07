package com.mejdoo.clean

import android.app.Application
import com.mejdoo.clean.di.cacheModule
import com.mejdoo.clean.di.dataSourceModule
import com.mejdoo.clean.di.networkModule
import com.mejdoo.clean.di.repositoryModule
import com.mejdoo.clean.di.useCaseModule
import com.mejdoo.clean.di.viewModelModule
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
                cacheModule
            )
        }
    }
}