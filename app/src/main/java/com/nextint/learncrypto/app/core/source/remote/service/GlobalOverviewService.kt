package com.nextint.learncrypto.app.core.source.remote.service

import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import retrofit2.http.GET

interface GlobalOverviewService
{
    @GET("global")
    suspend fun getMarketOverview() : MarketOverviewResponse
}