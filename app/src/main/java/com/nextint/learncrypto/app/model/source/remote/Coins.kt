package com.nextint.learncrypto.app.model.source.remote

import com.nextint.learncrypto.app.model.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.model.source.remote.response.CoinsResponse
import com.nextint.learncrypto.app.model.source.remote.response.ExchangeByCoinIdResponse
import com.nextint.learncrypto.app.model.source.remote.response.MarketsByCoinIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Coins {

    @GET("coins")
    suspend fun getAllCoins() : CoinsResponse

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