package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.search.data.ISearchRepository
import com.nextint.learncrypto.app.features.search.data.SearchRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class SearchModule
{
    @Binds
    abstract fun provideRepository(searchRepository: SearchRepository) : ISearchRepository
}