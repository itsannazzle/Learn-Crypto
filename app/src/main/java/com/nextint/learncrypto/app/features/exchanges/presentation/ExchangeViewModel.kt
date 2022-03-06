package com.nextint.learncrypto.app.features.exchanges.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ExchangeViewModel @Inject constructor(private val exchangeUseCase: ExchangeUseCase) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _getAllExchange : MutableLiveData<ApiResponse<List<ExchangesResponseItem>>> = MutableLiveData()
    val getAllExchange : LiveData<ApiResponse<List<ExchangesResponseItem>>> get() = _getAllExchange

    private val _getExchangeById : MutableLiveData<ApiResponse<ExchangesResponseItem>> = MutableLiveData()
    val getExchangeById : LiveData<ApiResponse<ExchangesResponseItem>> get() = _getExchangeById

    fun getAllExchange()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                exchangeUseCase.getExchanges().collect()
                {
                    _getAllExchange.postValue(it)
                }
            }
            catch (exception : Exception)
            {
                _message.postValue(exception.message.toString())
            }
            _loading.postValue(false)
        }
    }

    fun getExchangeById(stringExchangeId : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                exchangeUseCase.getExchangeById(stringExchangeId).collect()
                {
                    _getExchangeById.postValue(it)
                }
            }
            catch (exception: Exception)
            {
                _message.postValue(exception.message.toString())
            }
            _loading.postValue(false)
        }
    }
    
}










