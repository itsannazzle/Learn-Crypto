package com.nextint.learncrypto.app.features.overview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.features.coins.data.CoinsUseCase
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.features.overview.data.OverviewUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class  OverviewViewModel @Inject constructor(
    private val overviewUseCase: OverviewUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _marketOverview : MutableLiveData<ApiResponse<MarketOverviewResponse>> = MutableLiveData()
    val marketOverview : LiveData<ApiResponse<MarketOverviewResponse>> get() = _marketOverview




    fun getMarketOverview()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                overviewUseCase.getMarketOverview().collect {
                    _marketOverview.postValue(it)
                    Timber.d("dalem try")
                }
            } catch (e : java.lang.Exception){
                _message.postValue(e.message)
                Timber.d("dalem catch")
            }
            _loading.postValue(false)
            Timber.d("diluar")
        }
    }


}