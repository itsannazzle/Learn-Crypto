package com.nextint.learncrypto.app

import android.app.Application
import com.nextint.learncrypto.app.core.di.CoreComponent
import com.nextint.learncrypto.app.core.di.DaggerCoreComponent
import com.nextint.learncrypto.app.di.AppComponent
import com.nextint.learncrypto.app.di.DaggerAppComponent
import timber.log.Timber

open class CryptoApp : Application(){

    private val coreComponent : CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }



}