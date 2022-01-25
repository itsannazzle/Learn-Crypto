package com.nextint.learncrypto.app.core.source.remote.di

import android.app.Application
import com.nextint.learncrypto.app.core.source.remote.di.module.NetworkModule


interface CryptoAppComponent : CryptoAppDeps {

    fun inject(application: Application)

    interface Factory {
        //fun inject(@BindsInstance contex : Context) : CryptoAppComponent
        fun inject(networkModule: NetworkModule): CryptoAppComponent
    }

}