package com.nextint.learncrypto.app.features.coins.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.features.coins.domain.CoinsUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinsViewModel @Inject constructor(private val coinsUseCase : CoinsUseCase) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _coins: MutableLiveData<ApiResponse<List<CoinsResponseItem>>> = MutableLiveData()
    val coins: LiveData<ApiResponse<List<CoinsResponseItem>>> = _coins

    private val _coinById : MutableLiveData<ApiResponse<CoinByIdResponse>> = MutableLiveData()
    val coinById : LiveData<ApiResponse<CoinByIdResponse>> get() = _coinById

    fun getCoins()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                coinsUseCase.getAllCoins().collect()
                {
                    _coins.postValue(it)
                }
            }
            catch (exception: Exception)
            {
                _message.postValue(exception.message)
            }
            finally
            {
                _loading.postValue(false)
            }
        }
    }

    fun getCoinById(stringCoinId : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                coinsUseCase.getCoinById(stringCoinId).collect ()
                {
                    _coinById.postValue(it)
                }
            }
            catch (exception : Exception)
            {
                _message.postValue(exception.message)
            }
            finally
            {
                _loading.postValue(false)
            }
        }
    }

}