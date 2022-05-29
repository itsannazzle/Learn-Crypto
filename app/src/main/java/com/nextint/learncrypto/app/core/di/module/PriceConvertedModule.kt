package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.price_converter.data.IPriceConverterRepository
import com.nextint.learncrypto.app.features.price_converter.data.PriceConverterRepository
import com.nextint.learncrypto.app.features.search.data.ISearchRepository
import com.nextint.learncrypto.app.features.search.data.SearchRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class PriceConvertedModule
{
    @Binds
    abstract fun provideRepository(priceConverterRepository: PriceConverterRepository) : IPriceConverterRepository
}