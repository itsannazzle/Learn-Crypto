package com.nextint.learncrypto.app.features.exchanges.data

import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponse
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExchangeUseCase {

    suspend fun getExchanges() : Flow<ApiResponse<ExchangesResponse>>

    suspend fun getExchangeById(stringExchangeId : String) : Flow<ApiResponse<ExchangesResponseItem>>
}

interface IExchangeRepository {

    suspend fun getExchanges() : Flow<ApiResponse<ExchangesResponse>>

    suspend fun getExchangeById(stringExchangeId: String) : Flow<ApiResponse<ExchangesResponseItem>>

}

class ExchangeUseCaseImpl @Inject constructor(private val iExchangeRepository: IExchangeRepository) : ExchangeUseCase
{
    override suspend fun getExchanges(): Flow<ApiResponse<ExchangesResponse>> {
        return iExchangeRepository.getExchanges()
    }

    override suspend fun getExchangeById(stringExchangeId: String): Flow<ApiResponse<ExchangesResponseItem>> {
        return iExchangeRepository.getExchangeById(stringExchangeId)
    }
}