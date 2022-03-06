package com.nextint.learncrypto.app.features.person.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.di.AppScope
import com.nextint.learncrypto.app.features.person.domain.PeopleUseCase
import javax.inject.Inject

@AppScope
class PeopleViewModelFactory @Inject constructor(private val peopleUseCase: PeopleUseCase) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return when
        {
            modelClass.isAssignableFrom(PeopleViewModel::class.java) ->
            {
                PeopleViewModel(peopleUseCase) as T
            }
            else -> throw Throwable("Unknown View Model Class "+modelClass.name)
        }
    }
}