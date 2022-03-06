package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.exchanges.data.ExchangeRepository
import com.nextint.learncrypto.app.features.exchanges.data.IExchangeRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class ExchangeModule
{
    @Binds
    abstract fun provideRepository(exchangeRepository: ExchangeRepository) : IExchangeRepository
}