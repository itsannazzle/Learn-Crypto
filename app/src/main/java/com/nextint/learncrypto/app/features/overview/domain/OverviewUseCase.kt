package com.nextint.learncrypto.app.features.overview.domain

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.features.overview.data.IOverviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface OverviewUseCase
{
    suspend fun getMarketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
}

//butuh implementasi dari usecase jadi harus di bind
class OverviewUseCaseImpl @Inject constructor(private val iOverviewRepository: IOverviewRepository) : OverviewUseCase
{
    override suspend fun getMarketOverview(): Flow<ApiResponse<MarketOverviewResponse>>
    {
        return iOverviewRepository.getMarketOverview()
    }
}
