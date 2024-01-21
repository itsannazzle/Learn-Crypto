package com.nextint.learncrypto.app.features.price_converter.data

import com.nextint.learncrypto.app.core.source.remote.network.PriceConverterNetwork
import com.nextint.learncrypto.app.core.source.remote.response.*
import com.nextint.learncrypto.app.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceConverterRepository @Inject constructor(private val remoteData : PriceConverterNetwork) : IPriceConverterRepository
{
    override suspend fun getPriceConverted(stringBaseCurrency : String, stringQuotedCurrency : String, stringAmount : Int): Flow<ApiResponse<PriceConvertedResponse>>
    {
        return remoteData.getSearchResult(stringBaseCurrency, stringQuotedCurrency, stringAmount)
    }

}

//dipanggil di repository
interface IPriceConverterRepository
{
    suspend fun getPriceConverted(stringBaseCurrency : String, stringQuotedCurrency : String, stringAmount : Int) : Flow<ApiResponse<PriceConvertedResponse>>
}