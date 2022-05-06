package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.ExchangeByCoinIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsService {

    @GET("coins")
    suspend fun getAllCoins() : List<CoinsResponseItem>

    @GET("coins/{coin_id}")
    suspend fun getCoinById(
        @Path("coin_id") coinId : String
    ) : CoinByIdResponse

    @GET("coins/{coin_id}/markets")
    suspend fun getExchangesByCoinId(
        @Path("coin_id") coinId: String
    ) : ExchangeByCoinIdResponse

    @GET("coins/{coin_id}/markets")
    suspend fun getMarketByCoinId(
        @Path("coin_id") coinId: String
    ) : MarketsByCoinIdResponse

}