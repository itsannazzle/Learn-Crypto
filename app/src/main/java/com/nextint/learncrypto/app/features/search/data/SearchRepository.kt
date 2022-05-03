package com.nextint.learncrypto.app.features.search.data

import com.nextint.learncrypto.app.core.source.remote.network.SearchNetwork
import com.nextint.learncrypto.app.core.source.remote.response.SearchResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val remoteData : SearchNetwork) : ISearchRepository
{
    override suspend fun searchWithKeyword(stringKeyword: String): Flow<ApiResponse<SearchResponse>>
    {
        return remoteData.getSearchResult(stringKeyword)
    }
}

interface ISearchRepository
{
    suspend fun searchWithKeyword(stringKeyword : String) : Flow<ApiResponse<SearchResponse>>
}