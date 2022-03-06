package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MarketService
{
    @GET("coins/{coin_id}/markets")
    suspend fun getMarketByCoinId(@Path("coin_id") stringCoinId : String) : List<MarketsByCoinIdResponseItem>
}