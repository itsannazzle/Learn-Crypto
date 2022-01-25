package com.nextint.learncrypto.app.core.source.remote.di.module

import com.nextint.learncrypto.app.core.domain.repository.ICoinsRepository
import com.nextint.learncrypto.app.features.home.data.CoinsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class CoinModule {

    @Binds //what?
    abstract fun provideRepository(coinsRepository : CoinsRepository) : ICoinsRepository
}