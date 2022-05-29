package com.nextint.learncrypto.app.features.price_converter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.PriceConvertedResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.features.coins.domain.CoinsUseCase
import com.nextint.learncrypto.app.features.price_converter.domain.PriceConverterUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PriceConverterViewModel @Inject constructor(private val priceConverterUseCase: PriceConverterUseCase) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private val _priceConverted : MutableLiveData<ApiResponse<PriceConvertedResponse>> = MutableLiveData()
    val priceConverted : LiveData<ApiResponse<PriceConvertedResponse>> get() = _priceConverted

    fun convertPrice(stringBaseCurrency : String, stringQuotedCurrency : String, stringAmount : Int)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                priceConverterUseCase.getCoinById(stringBaseCurrency, stringQuotedCurrency, stringAmount).collect()
                {
                    _priceConverted.postValue(it)
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


}