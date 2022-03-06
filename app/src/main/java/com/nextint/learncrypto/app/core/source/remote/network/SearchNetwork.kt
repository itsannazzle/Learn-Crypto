package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.SearchExchangesItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNetwork @Inject constructor(private val searchService : SearchService) : BaseService()
{
    suspend fun getSearchResult(stringKeyword : String) : Flow<ApiResponse<List<SearchExchangesItem>>>
    {
        return flow ()
        {
            if (!checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            }
            else
            {
                try
                {
                    val response = searchService.search(stringKeyword)
                    if (response.exchanges.isNotEmpty())
                    {
                        emit(ApiResponse.Success(response.exchanges))
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
}