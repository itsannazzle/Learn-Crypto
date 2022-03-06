package com.nextint.learncrypto.app.core.di.module

import com.nextint.learncrypto.app.features.concept.data.ITagsRepository
import com.nextint.learncrypto.app.features.concept.data.TagsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class TagsModule
{

    @Binds
    abstract fun provideRepository(tagsRepository: TagsRepository) : ITagsRepository
}