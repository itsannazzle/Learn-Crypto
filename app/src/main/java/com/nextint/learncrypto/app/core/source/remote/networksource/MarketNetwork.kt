package com.nextint.learncrypto.app.core.source.remote.networksource

import android.accounts.NetworkErrorException
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.MarketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketNetwork @Inject constructor(private val marketService: MarketService)
{
    suspend fun getMarketByCoin(stringCoinId : String) : Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
    {
        return flow()
        {
            val response = marketService.getMarketByCoinId(stringCoinId)
            try
            {
                if (response.marketsByCoinIdResponse.isNotEmpty())
                {
                    emit(ApiResponse.Success(response.marketsByCoinIdResponse))
                } else
                {
                    emit(ApiResponse.Empty)
                }
            } catch (exception : HttpException)
            {
                emit(ApiResponse.Error(exception.message.toString()))
            }

        }
    }
}