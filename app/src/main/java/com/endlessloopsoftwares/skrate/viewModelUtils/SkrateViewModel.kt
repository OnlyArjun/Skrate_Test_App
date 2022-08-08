package com.endlessloopsoftwares.skrate.viewModelUtils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.endlessloopsoftwares.skrate.models.ApiResponse
import com.endlessloopsoftwares.skrate.repository.Repository
import com.endlessloopsoftwares.skrate.responseUtils.ResponseStates
import com.endlessloopsoftwares.skrate.applications.MyApplication
import kotlinx.coroutines.launch
import retrofit2.Response

class SkrateViewModel(
    currApp: Application,
    private val repoVar: Repository
) : AndroidViewModel(currApp) {
    val apiResponsesObject: MutableLiveData<ResponseStates<ApiResponse>> = MutableLiveData()

    init {
        makeApiCalls()
    }

    private fun makeApiCalls() = viewModelScope.launch {
        if (isInternetAvailable()) {
            try {
                val apiResponse = repoVar.makeApiCalls()
                apiResponsesObject.postValue(handleResponses(apiResponse))
            } catch (exceptionOccurred: Throwable) {
                apiResponsesObject.postValue(ResponseStates.ErrorCL("Error Occurred"))
            }
        } else {
            apiResponsesObject.postValue(ResponseStates.ErrorCL("No Internet"))
        }
    }

    private fun handleResponses(apiResponse: Response<ApiResponse>): ResponseStates<ApiResponse> {
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return ResponseStates.SuccessCL(it)
            }
        }

        return ResponseStates.ErrorCL("Api Error Occurred")
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        return connectivityManager.run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        } ?: false
    }
}