package com.nextint.learncrypto.app.features.tags.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.tags.data.TagsUseCase
import javax.inject.Inject

@AppScope
class TagsViewModelFactory @Inject constructor(private val tagsUseCase: TagsUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TagsViewModel::class.java) ->
            {
                TagsViewModel(tagsUseCase) as T
            }
            else -> throw  Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}