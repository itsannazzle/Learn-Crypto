package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface CryptoExchangeService {

    @GET("exchanges")
    suspend fun getExchanges() : List<ExchangesResponseItem>

    @GET("exchanges/{exchange_id}")
    suspend fun getExchangesById(
        @Path("exchange_id") id : String
    ) : ExchangesResponseItem

}