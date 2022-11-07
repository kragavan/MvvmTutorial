package com.example.mvvmtutorial.data.repository

import com.example.mvvmtutorial.data.UserPreferences
import com.example.mvvmtutorial.data.network.AuthApi
import com.example.mvvmtutorial.data.network.UserApi

class UserRepository(
  private val api: UserApi,
) : BaseRepository() {

 suspend fun getUser() = safeApiCall {
   api.getUser()

 }
}
