package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.GlobalOverviewService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverviewNetwork @Inject constructor(private val globalOverviewService: GlobalOverviewService) : BaseService()
{
    suspend fun getMarketOverview() : Flow<ApiResponse<MarketOverviewResponse>>
    {
        return flow ()
        {
            if (!checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            }
            else
            {
                try
                {
                    val response = globalOverviewService.getMarketOverview()
                    emit(ApiResponse.Success(response))
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }
    }
}