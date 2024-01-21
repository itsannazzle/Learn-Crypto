package com.nextint.learncrypto.app.features.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.SearchResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.features.search.domain.SearchUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchUsecase : SearchUseCase) : ViewModel()
{
    private var _searchResponse : MutableLiveData<ApiResponse<SearchResponse>> = MutableLiveData()
    val searchResponse : LiveData<ApiResponse<SearchResponse>> get() = _searchResponse

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    fun searchWithKeyword(stringKeyword : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
             searchUsecase.searchWithKeyword(stringKeyword).collect()
             {
                 _searchResponse.postValue(it)
             }
            } catch (exception: Exception)
            {
                _message.postValue(exception.message.toString())
            }
            _loading.postValue(false)
        }
    }
}