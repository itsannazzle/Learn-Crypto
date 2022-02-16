package com.nextint.learncrypto.app.features.exchanges.data

import com.nextint.learncrypto.app.core.source.remote.networksource.ExchangesNetwork
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponse
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepository @Inject constructor(private val remoteData : ExchangesNetwork) : IExchangeRepository
{
    override suspend fun getExchanges(): Flow<ApiResponse<List<ExchangesResponseItem>>> {
        return remoteData.getExchanges()
    }

    override suspend fun getExchangeById(stringExchangeId: String): Flow<ApiResponse<ExchangesResponseItem>> {
        return remoteData.getExchangesById(stringExchangeId)
    }
}