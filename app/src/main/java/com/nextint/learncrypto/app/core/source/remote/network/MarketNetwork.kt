package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.MarketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketNetwork @Inject constructor(private val marketService: MarketService) : BaseService()
{
    suspend fun getMarketByCoin(stringCoinId : String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
    {
        return flow()
        {
            if (!checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            }
            else
            {
                try
                {
                    val response = marketService.getMarketByCoinId(stringCoinId)
                    if (response.isNotEmpty())
                    {
                        emit(ApiResponse.Success(response.take(10)))
                    } else
                    {
                        emit(ApiResponse.Empty)
                    }

                }
                catch (httpException: HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }
    }
}