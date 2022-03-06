package com.nextint.learncrypto.app.features.person.data

import com.nextint.learncrypto.app.core.source.remote.network.PeopleNetwork
import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(private val remoteData : PeopleNetwork) : IPeopleRepository
{
    override suspend fun getPeopleById(stringPeopleId: String): Flow<ApiResponse<PeopleResponse>>
    {
        return remoteData.getPeopleById(stringPeopleId)
    }
}

interface IPeopleRepository
{
    suspend fun getPeopleById(stringPeopleId: String) : Flow<ApiResponse<PeopleResponse>>
}