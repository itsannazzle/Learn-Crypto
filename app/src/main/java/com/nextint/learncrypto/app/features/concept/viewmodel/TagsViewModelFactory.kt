package com.nextint.learncrypto.app.features.concept.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.concept.data.TagsUseCase
import javax.inject.Inject

@AppScope
class TagsViewModelFactory @Inject constructor(private val useCase: TagsUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TagsViewModel::class.java) ->
            {
                TagsViewModel(useCase) as T
            }
            else -> throw  Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}