package com.nextint.learncrypto.app.di

import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCaseImpl
import com.nextint.learncrypto.app.features.concept.data.TagsUseCase
import com.nextint.learncrypto.app.features.concept.data.TagsUseCaseImpl
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun provideCoinsUseCase(coinsUseCaseImpl: CoinsUseCaseImpl) : CoinsUseCase

    @Binds
    abstract fun provideTagsUseCase(tagsUseCaseImpl: TagsUseCaseImpl) : TagsUseCase
}