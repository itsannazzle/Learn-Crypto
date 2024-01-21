package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.CryptoExchangeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangesNetwork @Inject constructor(private val exchangeService: CryptoExchangeService) : BaseService()
{
    suspend fun getExchanges() : Flow<ApiResponse<List<ExchangesResponseItem>>>
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
                    val response = exchangeService.getExchanges()
                    if (response.isNotEmpty())
                    {
                        val responseFiltered = response.filter { it.confidenceScore!! > 0.05}.sortedByDescending { it.markets }
                        emit(ApiResponse.Success(responseFiltered.take(100)))
                    } else
                    {
                        emit(ApiResponse.Empty)
                    }
                } 
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }

        }
    }

    suspend fun getExchangesById(stringIdExchange : String) : Flow<ApiResponse<ExchangesResponseItem>>
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
                    val response = exchangeService.getExchangesById(stringIdExchange)
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