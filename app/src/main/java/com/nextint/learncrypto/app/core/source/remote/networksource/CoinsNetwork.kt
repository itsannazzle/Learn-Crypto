package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.CoinsService
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsNetwork @Inject constructor(private val coinsServiceService: CoinsService) {

    suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>> {
        return flow {
            coroutineScope {
                try {
                    val response = coinsServiceService.getAllCoins()
                    if (response.isNotEmpty()){
                        val newCoinByRanked = response.filter { it.isNew }.take(99).sortedBy { it.rank }
                        //emit(ApiResponse.Success(newCoinByRanked))
                        emit(ApiResponse.Success(response.take(99)))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } catch (e : Exception){
                    emit(ApiResponse.Error(e.message.toString()))
                }
            }
            }.flowOn(Dispatchers.IO)

    }

    suspend fun getCoinsById(coinId: String): CoinByIdResponse {
        return coinsServiceService.getCoinById(coinId)
    }

    suspend fun getExchangeByCoin(coinId: String): List<ExchangeByCoinIdResponseItem> {
        return coinsServiceService.getExchangesByCoinId(coinId).exchangeByCoinIdResponse
    }

    suspend fun getMarketByCoinId(coinId: String): List<MarketsByCoinIdResponseItem> {
        return coinsServiceService.getMarketByCoinId(coinId).marketsByCoinIdResponse
    }


}