package com.nextint.learncrypto.app.features.market.data

import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MarketUseCase
{
    suspend fun getMarketByCoin(stringCoinId : String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
}

interface IMarketRepository
{
    suspend fun getMarketByCoin(stringCoinId: String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
}

class MarketUseCaseImpl @Inject constructor(private val iMarketRepository: IMarketRepository) : MarketUseCase
{
    override suspend fun getMarketByCoin(stringCoinId: String): Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>> {
        return iMarketRepository.getMarketByCoin(stringCoinId)
    }
}