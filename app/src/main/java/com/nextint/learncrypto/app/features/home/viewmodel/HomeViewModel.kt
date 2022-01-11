package com.nextint.learncrypto.app.features.home.viewmodel

import androidx.lifecycle.*
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

class HomeViewModel @Inject constructor (
    private val useCase: CoinsUseCase
) : ViewModel() {
    
    private val _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    private val _message : MutableLiveData<String> = MutableLiveData()
    val message : LiveData<String> = _message

    private val _coins : MutableLiveData<ApiResponse<List<CoinsResponseItem>>> = MutableLiveData()
    val coins : LiveData<ApiResponse<List<CoinsResponseItem>>> = _coins

    init {
        getCoins()
    }
    fun getCoins(){
        viewModelScope.launch {
            useCase.getAllCoins().collect {
                _coins.postValue(it)
            }
        }
    }
}