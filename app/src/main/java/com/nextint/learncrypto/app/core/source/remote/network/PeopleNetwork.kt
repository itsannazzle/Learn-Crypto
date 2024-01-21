package com.nextint.learncrypto.app.core.source.remote.network

import com.nextint.learncrypto.app.bases.BaseService
import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.util.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.PeopleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleNetwork @Inject constructor(private val peopleService: PeopleService) : BaseService()
{
    suspend fun getPeopleById(stringPeopleId : String) : Flow<ApiResponse<PeopleResponse>>
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
                    val response = peopleService.getPeopleById(stringPeopleId)
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