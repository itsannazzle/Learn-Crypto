package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.Coins
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsNetwork @Inject constructor(private val coinsSerivce: Coins) {

    suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>> {
        return flow {
            try {
                val response = coinsSerivce.getAllCoins()
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
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCoinsById(coinId: String): CoinByIdResponse {
        return coinsSerivce.getCoinById(coinId)
    }

    suspend fun getExchangeByCoin(coinId: String): List<ExchangeByCoinIdResponseItem> {
        return coinsSerivce.getExchangesByCoinId(coinId).exchangeByCoinIdResponse
    }

    suspend fun getMarketByCoinId(coinId: String): List<MarketsByCoinIdResponseItem> {
        return coinsSerivce.getMarketByCoinId(coinId).marketsByCoinIdResponse
    }
}