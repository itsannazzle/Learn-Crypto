package com.nextint.learncrypto.app.features.overview.data

import com.nextint.learncrypto.app.core.source.remote.network.OverviewNetwork
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverviewRepository @Inject constructor(private val overviewNetwork: OverviewNetwork) : IOverviewRepository
{
    override suspend fun getMarketOverview(): Flow<ApiResponse<MarketOverviewResponse>>
    {
        return overviewNetwork.getMarketOverview()
    }
}

interface IOverviewRepository
{
    suspend fun getMarketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
}