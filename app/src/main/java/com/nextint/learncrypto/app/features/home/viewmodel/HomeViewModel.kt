package com.nextint.learncrypto.app.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.domain.repository.CoinsUseCase
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val useCase: CoinsUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _coins: MutableLiveData<ApiResponse<List<CoinsResponseItem>>> = MutableLiveData()
    val coins: LiveData<ApiResponse<List<CoinsResponseItem>>> = _coins

    private val _marketOverview : MutableLiveData<ApiResponse<MarketOverviewResponse>> = MutableLiveData()
    val marketOverview : LiveData<ApiResponse<MarketOverviewResponse>> get() = _marketOverview

    init {
        getCoins()
        getMarketOverview()
    }

    private fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCase.getAllCoins().collect {
                    _coins.postValue(it)
                }
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
            _loading.postValue(false)
        }
    }

    private fun getMarketOverview()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                useCase.getMarketOverview().collect {
                    _marketOverview.postValue(it)
                }
            } catch (e : java.lang.Exception){
                _message.postValue(e.message)
            }
            Thread.sleep(5000)
            _loading.postValue(false)
        }
    }


}