package com.example.mvvmtutorial.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Establish and perform Remote API calls using this class
 *
 */
class RemoteDataSource @Inject constructor() {

  companion object {
    private const val BASE_URL = "http://10.0.2.2:3000/"
  }

  fun <Api> buildApi(
    api: Class<Api>,
    authToken: String? = null
  ): Api {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(
        OkHttpClient.Builder()
          .addInterceptor { chain ->  
            chain.proceed(chain.request().newBuilder().also {
              it.addHeader("Authorization", "Bearer:$authToken")
            }.build())
          }.also {
              client ->
            val logging = HttpLoggingInterceptor()
            logging.setLevel(BODY)
            client.addInterceptor(logging)
          }.build()
      )
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(api)
  }
}