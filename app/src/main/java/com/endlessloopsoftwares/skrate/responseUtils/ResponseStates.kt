package com.endlessloopsoftwares.skrate.responseUtils

sealed class ResponseStates<T>(
    val responseData: T? = null,
    val responseMessage: String? = null
) {
    class SuccessCL<T>(dataGiven: T): ResponseStates<T>(dataGiven)
    class ErrorCL<T>(messagePassed: String, dataGiven: T? = null): ResponseStates<T>(dataGiven, messagePassed)
}