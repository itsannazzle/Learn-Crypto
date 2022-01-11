package com.nextint.learncrypto.app.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.di.AppScope
import javax.inject.Inject

@AppScope
class HomeViewModelFactory @Inject constructor(private val coinsUseCase: CoinsUseCase) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass) idk why we commeted this. to findout
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(coinsUseCase) as T
            }
            else -> throw Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}