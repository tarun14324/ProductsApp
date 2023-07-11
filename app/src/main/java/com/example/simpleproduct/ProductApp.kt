package com.example.simpleproduct

import android.app.Application
import com.example.simpleproduct.di.appModule
import com.example.simpleproduct.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * From your Application class you can use the startKoin function and inject the Android context with androidContext
 * */
class ProductApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        // by using this function you can also configure several parts of Koin.
        startKoin {
            //inject Android context
            androidContext(this@ProductApp)
            // load modules
           // In modularized projects, it allows you more fine control over a module visibility
           // all modules will be included only once
            modules(
                listOf(
                    appModule,
                    viewModelModule
                )
            )
        }
    }
}