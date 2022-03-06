package com.nextint.learncrypto.app.features.overview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.overview.domain.OverviewUseCase
import javax.inject.Inject

@AppScope
class OverviewViewModelFactory @Inject constructor(private val overviewUseCase: OverviewUseCase) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass) idk why we commeted this. to findout
        return when{
            modelClass.isAssignableFrom(OverviewViewModel::class.java) -> {
                OverviewViewModel(overviewUseCase) as T
            }
            else -> throw Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}