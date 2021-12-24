package com.nextint.learncrypto.app.model.di

import android.app.Application
import android.content.Context
import com.nextint.learncrypto.app.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SessionModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(app : Application)
    fun inject(mainActivity: MainActivity)

}