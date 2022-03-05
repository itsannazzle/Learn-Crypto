package com.nextint.learncrypto.app.features.market.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.features.market.data.MarketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class MarketViewModel @Inject constructor(private val useCase: MarketUseCase) : ViewModel()
{
    private val _marketByCoin : MutableLiveData<ApiResponse<List<MarketsByCoinIdResponseItem>>> = MutableLiveData()
    val marketByCoin : LiveData<ApiResponse<List<MarketsByCoinIdResponseItem>>> get() = _marketByCoin

    private val _message : MutableLiveData<String> = MutableLiveData()
    val message : LiveData<String> get() = _message

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(true)
    val loading : LiveData<Boolean> get() = _loading

    fun getMarketByCoin(stringCoinId : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                useCase.getMarketByCoin(stringCoinId).collect()
                {
                    _marketByCoin.postValue(it)
                }
            } catch (exception: Exception) {
                _message.postValue(exception.message.toString())
            }
            _loading.postValue(false)
        }
    }
}

