package com.nextint.learncrypto.app.features.market.data

import com.nextint.learncrypto.app.core.source.remote.network.MarketNetwork
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRepository @Inject constructor(private val marketNetwork: MarketNetwork) : IMarketRepository
{
    override suspend fun getMarketByCoin(stringCoinId: String): Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
    {
        return marketNetwork.getMarketByCoin(stringCoinId)
    }
}

interface IMarketRepository
{
    suspend fun getMarketByCoin(stringCoinId: String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
}