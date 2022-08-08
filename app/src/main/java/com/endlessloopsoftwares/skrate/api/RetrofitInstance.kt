package com.endlessloopsoftwares.skrate.api

import com.endlessloopsoftwares.skrate.models.ApiResponse
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofitInstance by lazy {
            val loggingVar = HttpLoggingInterceptor()
            loggingVar.setLevel(HttpLoggingInterceptor.Level.BODY)
            val clientVar = okhttp3.OkHttpClient.Builder()
                .addInterceptor(loggingVar)
                .build()

            Retrofit.Builder()
                .baseUrl("https://mocki.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientVar)
                .build()
        }

        val apiVar: ApiInterface by lazy {
            retrofitInstance.create(ApiInterface::class.java)
        }
    }
}