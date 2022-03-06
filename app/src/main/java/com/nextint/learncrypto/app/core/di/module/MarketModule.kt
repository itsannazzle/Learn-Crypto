package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.market.data.IMarketRepository
import com.nextint.learncrypto.app.features.market.data.MarketRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class MarketModule
{
    @Binds
    abstract fun provideRepository(marketRepository: MarketRepository) : IMarketRepository
}