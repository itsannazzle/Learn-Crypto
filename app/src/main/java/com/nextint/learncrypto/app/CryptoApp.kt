package com.nextint.learncrypto.app

import android.app.Application
import com.nextint.learncrypto.app.model.di.AppComponent
import com.nextint.learncrypto.app.model.di.DaggerAppComponent
import timber.log.Timber

open class CryptoApp : Application(){

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}