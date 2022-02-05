package com.nextint.learncrypto.app.features.person.data

import com.nextint.learncrypto.app.core.source.remote.response.PeopleResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PeopleUseCase
{
    suspend fun getPeopleById(stringPeopleId : String) : Flow<ApiResponse<PeopleResponse>>
}

interface IPeopleRepository
{
    suspend fun getPeopleById(stringPeopleId: String) : Flow<ApiResponse<PeopleResponse>>
}

class PeopleUseCaseImpl @Inject constructor(private val iPeopleRepository: IPeopleRepository) : PeopleUseCase
{
    override suspend fun getPeopleById(stringPeopleId: String): Flow<ApiResponse<PeopleResponse>> {
        return iPeopleRepository.getPeopleById(stringPeopleId)
    }
}