package com.nextint.learncrypto.app.features.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.coins.domain.CoinsUseCase
import com.nextint.learncrypto.app.features.concept.domain.TagsUseCase
import javax.inject.Inject

@AppScope
class CoinsViewModelFactory @Inject constructor(private val coinsUseCase: CoinsUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CoinsViewModel::class.java) ->
            {
                CoinsViewModel(coinsUseCase) as T
            }
            else -> throw  Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}