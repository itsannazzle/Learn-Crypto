package com.nextint.learncrypto.app.features.coins.data

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//dipanggil di viewmodel
interface CoinsUseCase {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : CoinByIdResponse

    suspend fun getExchangesByCoinId(coinId : String) : List<ExchangeByCoinIdResponseItem>

    suspend fun getMarketByCoin(coinId: String) : List<MarketsByCoinIdResponseItem>

}
//dipanggil di repository
interface ICoinsRepository {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : CoinByIdResponse

    suspend fun getExchangesByCoinId(coinId : String) : List<ExchangeByCoinIdResponseItem>

    suspend fun getMarketByCoin(coinId: String) : List<MarketsByCoinIdResponseItem>

}

//connect repository and viewmodel
class CoinsUseCaseImpl @Inject constructor(private val coinsRepository: ICoinsRepository) :
    CoinsUseCase {
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

}