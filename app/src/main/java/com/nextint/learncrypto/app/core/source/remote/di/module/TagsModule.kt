package com.nextint.learncrypto.app.core.source.remote.di.module

import com.nextint.learncrypto.app.features.tags.data.ITagsRepository
import com.nextint.learncrypto.app.features.tags.data.TagsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class TagsModule
{

    @Binds
    abstract fun provideRepository(tagsRepository: TagsRepository) : ITagsRepository
}