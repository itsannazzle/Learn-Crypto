package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.service.PeopleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleNetwork @Inject constructor(private val peopleService: PeopleService)
{
    suspend fun getPeopleById(stringPeopleId : String) : Flow<ApiResponse<PeopleResponse>>
    {
        return flow()
        {
            val response = peopleService.getPeopleById(stringPeopleId)
            try
            {
                emit(ApiResponse.Success(response))
            } catch (exception : Exception)
            {
                emit(ApiResponse.Error(exception.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}