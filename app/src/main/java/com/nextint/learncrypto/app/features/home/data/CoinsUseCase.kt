package com.nextint.learncrypto.app.core.domain.repository

import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

interface CoinsUseCase {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : CoinByIdResponse

    suspend fun getExchangesByCoinId(coinId : String) : List<ExchangeByCoinIdResponseItem>

    suspend fun getMarketByCoin(coinId: String) : List<MarketsByCoinIdResponseItem>

    suspend fun getMarketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
}
interface ICoinsRepository {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : CoinByIdResponse

    suspend fun getExchangesByCoinId(coinId : String) : List<ExchangeByCoinIdResponseItem>

    suspend fun getMarketByCoin(coinId: String) : List<MarketsByCoinIdResponseItem>

    suspend fun getMaketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
}

class CoinsUseCaseImpl @Inject constructor(private val coinsRepository: ICoinsRepository) : CoinsUseCase{
    override suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>> {
        return coinsRepository.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinByIdResponse {
        return coinsRepository.getCoinById(coinId)
    }

    override suspend fun getExchangesByCoinId(coinId: String): List<ExchangeByCoinIdResponseItem> {
        return coinsRepository.getExchangesByCoinId(coinId).take(10)
    }

    override suspend fun getMarketByCoin(coinId: String): List<MarketsByCoinIdResponseItem> {
        return coinsRepository.getMarketByCoin(coinId)
    }

    override suspend fun getMarketOverview(): Flow<ApiResponse<MarketOverviewResponse>> {
        return coinsRepository.getMaketOverview()
    }
}