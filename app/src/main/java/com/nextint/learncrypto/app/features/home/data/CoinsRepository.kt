package com.nextint.learncrypto.app.features.home.data

import com.nextint.learncrypto.app.core.domain.repository.ICoinsRepository
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.Coins
import com.nextint.learncrypto.app.core.source.remote.networksource.CoinsNetwork
import com.nextint.learncrypto.app.core.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsRepository @Inject constructor(private val remoteData : CoinsNetwork) : ICoinsRepository{

    override suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>> {
        return remoteData.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinByIdResponse {
        return remoteData.getCoinsById(coinId)
    }

    override suspend fun getExchangesByCoinId(coinId: String): List<ExchangeByCoinIdResponseItem> {
        return remoteData.getExchangeByCoin(coinId)
    }

    override suspend fun getMarketByCoin(coinId: String): List<MarketsByCoinIdResponseItem> {
        return remoteData.getMarketByCoinId(coinId)
    }
}