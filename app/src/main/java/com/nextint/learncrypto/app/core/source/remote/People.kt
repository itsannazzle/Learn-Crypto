package com.nextint.learncrypto.app.core.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface People {
    @GET("people/{person_id}")
    suspend fun getPeopleById(
        @Path("person_id") personId : String
    )
}