package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponse
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.CryptoExchangeService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangesNetwork @Inject constructor(private val exchangeService: CryptoExchangeService)
{
    suspend fun getExchanges() : Flow<ApiResponse<ExchangesResponse>>
    {
        return flow {
            coroutineScope {
                try {
                    val response = exchangeService.getExchanges()
                    if (response.exchangesResponse.isNullOrEmpty())
                    {
                        emit(ApiResponse.Empty)
                    } else
                    {
                        emit(ApiResponse.Success(response))
                    }
                } catch (exception : Exception)
                {
                    emit(ApiResponse.Error(exception.message.toString()))
                }
            }
        }
    }

    suspend fun getExchangesById(stringIdExchange : String) : Flow<ApiResponse<ExchangesResponseItem>>
    {
        return flow {
            coroutineScope {
                try {
                    val response = exchangeService.getExchangesById(stringIdExchange)
                    emit(ApiResponse.Success(response))
                } catch (exception : Exception)
                {
                    emit(ApiResponse.Error(exception.message.toString()))
                }
            }
        }
    }
}