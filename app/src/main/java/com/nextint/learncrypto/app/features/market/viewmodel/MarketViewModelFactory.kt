package com.nextint.learncrypto.app.features.market.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.market.data.MarketUseCase
import javax.inject.Inject

@AppScope
class MarketViewModelFactory @Inject constructor(private val marketUseCase: MarketUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
        return when
        {
            modelClass.isAssignableFrom(MarketViewModel::class.java) ->
            {
                MarketViewModel(marketUseCase) as T
            }
            else -> throw Throwable("Unknown View Model Class "+modelClass.name)
        }

    }
}