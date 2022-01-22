package com.nextint.learncrypto.app.features.home.viewmodel

import androidx.lifecycle.*
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val useCase: CoinsUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _coins: MutableLiveData<ApiResponse<List<CoinsResponseItem>>> = MutableLiveData()
    val coins : LiveData<ApiResponse<List<CoinsResponseItem>>> = _coins

    val coinsData = liveData {
        try {
            useCase.getAllCoins().collect {
                emit(it)
                _loading.value = false
            }
        } catch (e: Exception) {
            _message.value = e.message
            _loading.value = false
        }
    }

    init {
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch {
            try {
                useCase.getAllCoins().collect {
                    _coins.postValue(it)
                }
            } catch (e: Exception) {
                _message.value = e.message
            }
            _loading.value = false
        }
    }


}