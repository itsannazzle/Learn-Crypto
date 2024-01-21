package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.PriceConvertedResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.PriceConverterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceConverterNetwork @Inject constructor(private val priceConverterService: PriceConverterService) : BaseService()
{
    suspend fun getSearchResult(stringBaseCurrency : String, stringQuotedCurrency : String, stringAmount : Int)
    : Flow<ApiResponse<PriceConvertedResponse>>
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
                    val response = priceConverterService.convertPrice(stringBaseCurrency, stringQuotedCurrency, stringAmount)
                    emit(ApiResponse.Success(response))
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}