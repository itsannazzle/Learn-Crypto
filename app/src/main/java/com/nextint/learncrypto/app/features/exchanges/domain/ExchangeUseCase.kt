package com.nextint.learncrypto.app.features.exchanges.data

import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExchangeUseCase
{
    suspend fun getExchanges() : Flow<ApiResponse<List<ExchangesResponseItem>>>

    suspend fun getExchangeById(stringExchangeId : String) : Flow<ApiResponse<ExchangesResponseItem>>
}


class ExchangeUseCaseImpl @Inject constructor(private val iExchangeRepository: IExchangeRepository) : ExchangeUseCase
{
    override suspend fun getExchanges(): Flow<ApiResponse<List<ExchangesResponseItem>>>
    {
        return iExchangeRepository.getExchanges()
    }

    override suspend fun getExchangeById(stringExchangeId: String): Flow<ApiResponse<ExchangesResponseItem>>
    {
        return iExchangeRepository.getExchangeById(stringExchangeId)
    }
}