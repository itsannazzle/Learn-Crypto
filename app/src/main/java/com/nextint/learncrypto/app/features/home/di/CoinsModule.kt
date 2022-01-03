package com.nextint.learncrypto.app.features.home.di

import com.nextint.learncrypto.app.features.home.data.CoinsRepository
import com.nextint.learncrypto.app.model.source.remote.di.NetworkModule
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class CoinsModule {
    @Binds
    abstract fun provideCoinsRepository(coinsRepository: CoinsRepository) :
}