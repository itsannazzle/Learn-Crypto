package com.nextint.learncrypto.app.core.source.remote.networksource

import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.Tags
import com.nextint.learncrypto.app.core.source.remote.response.TagsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsNetwork @Inject constructor(private val tagsService : Tags)
{
    suspend fun getAllTags() : Flow<ApiResponse<TagsResponse>>
    {
        return flow ()
        {
            val response = tagsService.getAllVocabulary()
            try
            {
                if (response.vocabularyResponse.isNotEmpty())
                {
                    emit(ApiResponse.Success(response))
                } else
                {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception)
            {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}