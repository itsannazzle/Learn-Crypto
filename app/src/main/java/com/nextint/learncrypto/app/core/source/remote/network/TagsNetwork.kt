package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.TagsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsNetwork @Inject constructor(private val tagsService : TagsService) : BaseService()
{
    suspend fun getAllTags() : Flow<ApiResponse<List<TagByIdResponse>>>
    {
        return flow ()
        {
            if (!checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            }
            else
            {
                val response = tagsService.getAllTag()
                try
                {
                    if (response.isNotEmpty())
                    {
                        emit(ApiResponse.Success(response))
                    } else
                    {
                        emit(ApiResponse.Empty)
                    }
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTagById(stringTagId : String) : Flow<ApiResponse<TagByIdResponse>>
    {
        return flow()
        {
            if (!checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            }
            else
            {
                try
                {
                    val response = tagsService.getTagById(stringTagId)
                    emit(ApiResponse.Success(response))
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}