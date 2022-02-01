package com.nextint.learncrypto.app.features.search

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchUseCase
{

}

interface ISearchRepository
{

}

class SearchUseCaseImpl @Inject constructor(private val iSearchRepository: ISearchRepository) : SearchUseCase
{

}
