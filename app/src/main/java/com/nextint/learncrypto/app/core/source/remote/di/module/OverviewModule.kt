package com.nextint.learncrypto.app.core.source.remote.di.module

import com.nextint.learncrypto.app.features.overview.data.IOverviewRepository
import com.nextint.learncrypto.app.features.overview.data.OverviewRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class OverviewModule
{
    @Binds
    abstract fun provideRepository(overviewRepository: OverviewRepository) : IOverviewRepository
}