package com.nextint.learncrypto.app.core.source.remote.service

sealed class ApiResponse<out R> {
    class Success<out T>(val data: T? = null) : ApiResponse<T>()
    class Error(val message: Int) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
    class InternetConnection(val status : Boolean) : ApiResponse<Nothing>()
}