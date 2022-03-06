package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.person.data.IPeopleRepository
import com.nextint.learncrypto.app.features.person.data.PeopleRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class PeopleModule
{
    @Binds
    abstract fun provideRepository(peopleRepository: PeopleRepository) : IPeopleRepository
}