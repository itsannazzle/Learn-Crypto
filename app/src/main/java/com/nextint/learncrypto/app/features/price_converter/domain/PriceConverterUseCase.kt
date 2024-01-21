package com.nextint.learncrypto.app.features.price_converter.domain

import com.nextint.learncrypto.app.core.source.remote.response.*
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.features.price_converter.data.IPriceConverterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//dipanggil di view model
interface PriceConverterUseCase
{
    suspend fun getCoinById(stringBaseCurrency : String, stringQuotedCurrency : String, stringAmount : Int) : Flow<ApiResponse<PriceConvertedResponse>>
}

//connect repository and view model
class PriceConverterUseCaseImpl @Inject constructor(private val iPriceConverterRepository: IPriceConverterRepository) : PriceConverterUseCase
{
    override suspend fun getCoinById(
        stringBaseCurrency: String,
        stringQuotedCurrency: String,
        stringAmount: Int
    ): Flow<ApiResponse<PriceConvertedResponse>> {
        return iPriceConverterRepository.getPriceConverted(stringBaseCurrency, stringQuotedCurrency, stringAmount)
    }
}