package com.nextint.learncrypto.app.features.search.domain

import com.nextint.learncrypto.app.core.source.remote.response.SearchResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.features.search.data.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchUseCase
{
    suspend fun searchWithKeyword(stringKeyword : String) : Flow<ApiResponse<SearchResponse>>
}



class SearchUseCaseImpl @Inject constructor(private val iSearchRepository: ISearchRepository) : SearchUseCase
{
    override suspend fun searchWithKeyword(stringKeyword: String): Flow<ApiResponse<SearchResponse>>
    {
        return iSearchRepository.searchWithKeyword(stringKeyword)
    }
}
