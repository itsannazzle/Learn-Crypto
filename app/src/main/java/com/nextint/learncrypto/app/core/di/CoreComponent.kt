package com.nextint.learncrypto.app.core.di

import android.content.Context
import com.nextint.learncrypto.app.core.domain.repository.ICoinsRepository
import com.nextint.learncrypto.app.core.source.remote.di.module.CoinModule
import com.nextint.learncrypto.app.core.source.remote.di.module.TagsModule
import com.nextint.learncrypto.app.features.concept.data.ITagsRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoinModule::class, TagsModule::class])
interface CoreComponent {
    @Component.Factory
    interface Factory{
        //why?
        fun create(@BindsInstance context: Context) : CoreComponent
    }

    fun provideCoinsImpl() : ICoinsRepository

    fun provideTagsImpl() : ITagsRepository
}