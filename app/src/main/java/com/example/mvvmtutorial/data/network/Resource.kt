package com.example.mvvmtutorial.data.network

import okhttp3.ResponseBody

/**
 * Provides a wrapper around API response
 *
 * @param T Expected Response class
 */
sealed class Resource<out T> {
  data class Success<out T>(val value: T) : Resource<T>()
  data class Failure(
    val isNetworkError: Boolean,
    val errorCode: Int?,
    val errorBody: ResponseBody?
  ) : Resource<Nothing>()
  object Loading : Resource<Nothing>()
}