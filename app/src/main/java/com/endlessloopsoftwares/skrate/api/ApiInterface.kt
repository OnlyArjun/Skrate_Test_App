package com.endlessloopsoftwares.skrate.api

import com.endlessloopsoftwares.skrate.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/bb11aecd-ba61-44b9-9e2c-beabc442d818")
    suspend fun makeApiCall(): Response<ApiResponse>
}