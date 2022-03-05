package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.MarketService
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketNetwork @Inject constructor(private val marketService: MarketService)
{
    suspend fun getMarketByCoin(stringCoinId : String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
    {
        return flow()
        {
            if (!UtilitiesFunction.checkInternetConnection())
            {
                emit(ApiResponse.InternetConnection(false))
            } else {
                try {
                    val response = marketService.getMarketByCoinId(stringCoinId)
                    if (response.isNotEmpty()) {
                        emit(ApiResponse.Success(response.take(10)))
                    } else {
                        emit(ApiResponse.Empty)
                    }

                } catch (exception: HttpException) {
                    emit(ApiResponse.Error(exception.code().toString()))
                }
            }
        }
    }
}