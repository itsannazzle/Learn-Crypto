package com.nextint.learncrypto.app.core.di

import android.content.Context
import com.nextint.learncrypto.app.core.di.module.*
import com.nextint.learncrypto.app.features.coins.data.ICoinsRepository
import com.nextint.learncrypto.app.features.concept.data.ITagsRepository
import com.nextint.learncrypto.app.features.exchanges.data.IExchangeRepository
import com.nextint.learncrypto.app.features.market.data.IMarketRepository
import com.nextint.learncrypto.app.features.overview.data.IOverviewRepository
import com.nextint.learncrypto.app.features.person.data.IPeopleRepository
import com.nextint.learncrypto.app.features.price_converter.data.IPriceConverterRepository
import com.nextint.learncrypto.app.features.search.data.ISearchRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CoinModule::class, TagsModule::class,
    SearchModule::class, OverviewModule::class,
    PeopleModule::class, ExchangeModule::class,
    MarketModule::class, PriceConvertedModule::class
])
interface CoreComponent {
    @Component.Factory
    interface Factory{
        //why?
        fun create(@BindsInstance context: Context) : CoreComponent
    }
    //provide implementation for @Binds

    fun provideCoinsImpl() : ICoinsRepository

    fun provideTagsImpl() : ITagsRepository

    fun provideSearchImpl() : ISearchRepository

    fun provideOverviewImpl() : IOverviewRepository

    fun providePeopleImpl() : IPeopleRepository

    fun provideExchangeImpl() : IExchangeRepository

    fun provideMarketImpl() : IMarketRepository

    fun providePriceConverterImpl() : IPriceConverterRepository

}