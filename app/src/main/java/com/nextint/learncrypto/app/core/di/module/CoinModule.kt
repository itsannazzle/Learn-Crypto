package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.coins.data.ICoinsRepository
import com.nextint.learncrypto.app.features.coins.data.CoinsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class CoinModule
{

    //class Repository need implementation of Icoinrepository, so here it goes.
    @Binds
    abstract fun provideRepository(coinsRepository : CoinsRepository) : ICoinsRepository
}