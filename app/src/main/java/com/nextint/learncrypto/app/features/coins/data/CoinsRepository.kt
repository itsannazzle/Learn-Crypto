package com.nextint.learncrypto.app.features.coins.data

import com.nextint.learncrypto.app.core.source.remote.network.CoinsNetwork
import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.ExchangeByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsRepository @Inject constructor(private val remoteData : CoinsNetwork) : ICoinsRepository
{

    override suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>>
    {
        return remoteData.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): Flow<ApiResponse<CoinByIdResponse>>
    {
        return remoteData.getCoinsById(coinId)
    }

    override suspend fun getExchangesByCoinId(coinId: String): Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>>
    {
        return remoteData.getExchangeByCoin(coinId)
    }

    override suspend fun getMarketByCoin(coinId: String): Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
    {
        return remoteData.getMarketByCoinId(coinId)
    }

}

//dipanggil di repository
interface ICoinsRepository
{
    suspend fun getAllCoins() : Flow<ApiResponse<List<CoinsResponseItem>>>

    suspend fun getCoinById(coinId : String) : Flow<ApiResponse<CoinByIdResponse>>

    suspend fun getExchangesByCoinId(coinId : String) : Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>>

    suspend fun getMarketByCoin(coinId: String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>

}