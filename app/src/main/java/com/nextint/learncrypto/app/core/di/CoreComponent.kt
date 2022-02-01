package com.nextint.learncrypto.app.core.di

import android.content.Context
import com.nextint.learncrypto.app.features.coins.data.ICoinsRepository
import com.nextint.learncrypto.app.core.source.remote.di.module.CoinModule
import com.nextint.learncrypto.app.core.source.remote.di.module.OverviewModule
import com.nextint.learncrypto.app.core.source.remote.di.module.SearchModule
import com.nextint.learncrypto.app.core.source.remote.di.module.TagsModule
import com.nextint.learncrypto.app.features.concept.data.ITagsRepository
import com.nextint.learncrypto.app.features.overview.data.IOverviewRepository
import com.nextint.learncrypto.app.features.search.ISearchRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoinModule::class, TagsModule::class, SearchModule::class, OverviewModule::class])
interface CoreComponent {
    @Component.Factory
    interface Factory{
        //why?
        fun create(@BindsInstance context: Context) : CoreComponent
    }

    fun provideCoinsImpl() : ICoinsRepository

    fun provideTagsImpl() : ITagsRepository

    fun provideSearchImpl() : ISearchRepository

    fun provideOverviewImpl() : IOverviewRepository

}