package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.SearchService
import com.nextint.learncrypto.app.core.source.remote.response.SearchExchangesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNetwork @Inject constructor(private val searchServiceService : SearchService)
{
    suspend fun getSearchResult(stringKeyword : String) : Flow<ApiResponse<List<SearchExchangesItem>>>
    {
        return flow ()
        {
            try
            {
                val response = searchServiceService.search(stringKeyword)
                if (response.exchanges.isNotEmpty())
                {
                    emit(ApiResponse.Success(response.exchanges))
                } else
                {
                    emit(ApiResponse.Empty)
                }
            } catch (exception : Exception)
            {
                emit(ApiResponse.Error(exception.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}