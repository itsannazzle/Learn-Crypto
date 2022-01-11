package com.nextint.learncrypto.app.core.source.remote.di

import com.nextint.learncrypto.app.core.domain.repository.ICoinsRepository
import com.nextint.learncrypto.app.features.home.di.CoinsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class CoinModule {

    @Binds //what?
    abstract fun provideRepository(coinsRepostiory : CoinsRepository) : ICoinsRepository
}