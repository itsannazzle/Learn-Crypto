package com.nextint.learncrypto.app.core.source.remote.service

sealed class ApiResponse<out R> {
    class Success<out T>(val data: T) : ApiResponse<T>()
    class Error(val message: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}