package com.nextint.learncrypto.app.features.tags.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import com.nextint.learncrypto.app.features.tags.data.TagsUseCase
import com.nextint.learncrypto.app.features.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class TagsViewModel @Inject constructor(private val tagUseCase: TagsUseCase) : ViewModel()
{
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private var _allTags : MutableLiveData<ApiResponse<List<TagByIdResponse>>> = MutableLiveData()
    val allTags : LiveData<ApiResponse<List<TagByIdResponse>>> get() = _allTags

    private var _tagById : MutableLiveData<ApiResponse<TagByIdResponse>> = MutableLiveData()
    val tagById : LiveData<ApiResponse<TagByIdResponse>> get() = _tagById

    fun getAllTags()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                tagUseCase.getAllTags().collect()
                {
                    _allTags.postValue(it)
                }
            } catch (exception : Exception)
            {
                _message.postValue(exception.message)
            }
            _loading.postValue(false)
        }
    }

    fun getTagById(stringTagId : String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                tagUseCase.getTagById(stringTagId).collect()
                {
                    _tagById.postValue(it)
                }
            } catch (exception : Exception)
            {
                _message.postValue(exception.message)
            }
            _loading.postValue(false)
        }
    }
}