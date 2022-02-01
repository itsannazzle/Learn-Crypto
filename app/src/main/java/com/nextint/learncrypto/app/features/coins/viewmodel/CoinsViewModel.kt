package com.nextint.learncrypto.app.features.coins.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.features.coins.data.CoinsUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinsViewModel @Inject constructor(private val coinsUsecase : CoinsUseCase ) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _coins: MutableLiveData<ApiResponse<List<CoinsResponseItem>>> = MutableLiveData()
    val coins: LiveData<ApiResponse<List<CoinsResponseItem>>> = _coins

    fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                coinsUsecase.getAllCoins().collect {
                    _coins.postValue(it)
                }
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
            _loading.postValue(false)
        }
    }

}