package com.nextint.learncrypto.app.di

import android.content.Context
import com.nextint.learncrypto.app.core.di.CoreComponent
import com.nextint.learncrypto.app.features.home.HomeFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: HomeFragment)
}