package com.nextint.learncrypto.app.model.source.remote

import com.nextint.learncrypto.app.model.source.remote.response.ExchangesResponse
import com.nextint.learncrypto.app.model.source.remote.response.ExchangesResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface CryptoExchange {

    @GET("exchanges")
    suspend fun getExchanges() : ExchangesResponse

    @GET("exchanges/{exchange_id}")
    suspend fun getExchangesById(
        @Path("exchanges_id") id : String
    ) : ExchangesResponseItem

}