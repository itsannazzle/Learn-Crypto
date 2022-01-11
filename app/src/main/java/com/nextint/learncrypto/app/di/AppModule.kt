package com.nextint.learncrypto.app.di

import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCaseImpl
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun provideCoinsUsecase(coinsUseCaseImpl: CoinsUseCaseImpl) : CoinsUseCase
}