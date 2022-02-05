package com.nextint.learncrypto.app.core.source.remote.di.module

import com.nextint.learncrypto.app.features.search.ISearchRepository
import com.nextint.learncrypto.app.features.search.SearchRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class SearchModule
{
    @Binds
    abstract fun provideRepository(searchRepository: SearchRepository) : ISearchRepository
}