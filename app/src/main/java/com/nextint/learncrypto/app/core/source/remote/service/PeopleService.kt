package com.nextint.learncrypto.app.core.source.remote.service

import retrofit2.http.GET
import retrofit2.http.Path

interface PeopleService {
    @GET("people/{person_id}")
    suspend fun getPeopleById(
        @Path("person_id") personId : String
    )
}