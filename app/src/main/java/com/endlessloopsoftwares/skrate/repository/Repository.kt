package com.endlessloopsoftwares.skrate.repository

import com.endlessloopsoftwares.skrate.api.RetrofitInstance

class Repository {
    suspend fun makeApiCalls() = RetrofitInstance.apiVar.makeApiCall()
}