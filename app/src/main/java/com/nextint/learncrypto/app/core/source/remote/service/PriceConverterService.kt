package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.PriceConvertedResponse
import com.nextint.learncrypto.app.core.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceConverterService
{
    @GET("price-converter")
    suspend fun convertPrice(
        @Query("base_currency_id") stringBaseCurrency: String,
        @Query("quote_currency_id") stringQuotedCurrency: String,
        @Query("amount") stringAmount: Int,
    ) : PriceConvertedResponse

}