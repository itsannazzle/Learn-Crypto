package com.nextint.learncrypto.app.features.coins.data

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

//dipanggil di viewmodel
interface CoinsUseCase {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : Flow<ApiResponse<CoinByIdResponse>>

    suspend fun getExchangesByCoinId(coinId : String) : Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>>

    suspend fun getMarketByCoin(coinId: String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>

}
//dipanggil di repository
interface ICoinsRepository {
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : Flow<ApiResponse<CoinByIdResponse>>

    suspend fun getExchangesByCoinId(coinId : String) : Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>>

    suspend fun getMarketByCoin(coinId: String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>

}

//connect repository and viewmodel
class CoinsUseCaseImpl @Inject constructor(private val coinsRepository: ICoinsRepository) :
    CoinsUseCase {
    override suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>> {
        return coinsRepository.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): Flow<ApiResponse<CoinByIdResponse>> {
        return coinsRepository.getCoinById(coinId)
    }

    override suspend fun getExchangesByCoinId(coinId: String): Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>> {
        return coinsRepository.getExchangesByCoinId(coinId).take(10)
    }

    override suspend fun getMarketByCoin(coinId: String): Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>> {
        return coinsRepository.getMarketByCoin(coinId)
    }

}