package com.nextint.learncrypto.app.model.di

import android.content.Context
import com.nextint.learncrypto.app.model.session.SessionManager
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

@Module
class SessionModule {

    @Provides
    fun provideSessionManager(context: Context) : SessionManager = SessionManager(context)
}