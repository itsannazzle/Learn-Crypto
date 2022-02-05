package com.nextint.learncrypto.app.features.person.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.features.person.data.PeopleUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val peopleUseCase: PeopleUseCase) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _peopleById : MutableLiveData<ApiResponse<PeopleResponse>> = MutableLiveData()
    val peopleById : LiveData<ApiResponse<PeopleResponse>> get() = _peopleById

    fun getPeopleById(stringPeopleId : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                peopleUseCase.getPeopleById(stringPeopleId).collect()
                {
                    _peopleById.postValue(it)
                }
            } catch (exception : Exception)
            {
                _message.postValue(exception.message)
            }
            _loading.postValue(false)
        }
    }
}