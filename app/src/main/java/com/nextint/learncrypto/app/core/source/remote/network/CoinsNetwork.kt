package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.CoinByIdResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.ExchangeByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.CoinsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsNetwork @Inject constructor(private val coinsService: CoinsService) : BaseService()
{
    suspend fun getAllCoins(): Flow<ApiResponse<List<CoinsResponseItem>>>
    {
        return flow()
        {
            if (!checkInternetConnection())
            {
                 emit(ApiResponse.InternetConnection(false))
            } else
            {
                try
                {
                    val response = coinsService.getAllCoins()
                    if (response.isNotEmpty()){
                        //val newCoinByRanked = response.filter { it.rank < 100 && it.isNew }.take(99)
                        // emit(ApiResponse.Success(newCoinByRanked.take(99)))
                        emit(ApiResponse.Success(response.take(99)))
                    }
                    else
                    {
                        emit(ApiResponse.Empty)
                    }
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
            }.flowOn(Dispatchers.IO)
    }

    suspend fun getCoinsById(stringCoinId: String): Flow<ApiResponse<CoinByIdResponse>>
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
                    val response = coinsService.getCoinById(stringCoinId)
                    emit(ApiResponse.Success(response))
                }
                catch (httpException : HttpException)
                {
                    emit(ApiResponse.Error(httpException.code()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getExchangeByCoin(stringCoinId: String): Flow<ApiResponse<List<ExchangeByCoinIdResponseItem>>>
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
                    val response = coinsService.getExchangesByCoinId(stringCoinId)
                    if (response.exchangeByCoinIdResponse.isNotEmpty())
                    {
                        emit(ApiResponse.Success(response.exchangeByCoinIdResponse))
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

    suspend fun getMarketByCoinId(stringCoinId: String): Flow<ApiResponse<List<MarketsByCoinIdResponseItem>>>
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
                    val response = coinsService.getMarketByCoinId(stringCoinId)
                    if (response.marketsByCoinIdResponse.isNotEmpty())
                    {
                        emit(ApiResponse.Success(response.marketsByCoinIdResponse.take(10)))
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

}