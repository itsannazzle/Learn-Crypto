package com.nextint.learncrypto.app.features.exchanges.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCase
import javax.inject.Inject

class ExchangeViewModelFactory @Inject constructor(private val exchangeUseCase: ExchangeUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when
        {
            modelClass.isAssignableFrom(ExchangeViewModel::class.java) ->
            {
                ExchangeViewModel(exchangeUseCase) as T
            }
            else -> throw Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}