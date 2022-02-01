package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.GlobalOverviewService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverviewNetwork @Inject constructor(private val globalOverviewService: GlobalOverviewService)
{
    suspend fun getMarketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
    {
        return flow ()
        {
            try
            {
                val response = globalOverviewService.getMarketOverview()
                emit(ApiResponse.Success(response))
            } catch (e: Exception)
            {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}