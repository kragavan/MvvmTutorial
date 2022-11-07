package com.example.mvvmtutorial.data.repository

import com.example.mvvmtutorial.data.UserPreferences
import com.example.mvvmtutorial.data.network.AuthApi

class AuthRepository(
  private val api: AuthApi,
  private val preferences: UserPreferences
) : BaseRepository() {

  suspend fun login(
    email: String,
    password: String
  ) = safeApiCall {
    api.login(email, password)
  }

  suspend fun saveAuthToken(token: String) {
    preferences.saveAuthToken(token)
  }
}