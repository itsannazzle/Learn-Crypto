package com.nextint.learncrypto.app.features.concept.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import com.nextint.learncrypto.app.features.concept.data.TagsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TagsViewModel @Inject constructor(private val useCase: TagsUseCase) : ViewModel()
{
    private var _allTags : MutableLiveData<ApiResponse<TagsResponse>> = MutableLiveData()
    val allTags : LiveData<ApiResponse<TagsResponse>> get() = _allTags

    private fun getAllTags()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
//            try {
//                val response
//            }
        }
    }
}