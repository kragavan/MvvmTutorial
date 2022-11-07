package com.example.mvvmtutorial.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.first

/**
 * Provides Data persistence and retrieval using Jetpack Preferences DataStore
 *
 * @property context Activity context
 */
class UserPreferences(val context: Context) {
  private val dataStore = context.createDataStore(name = "settings")

  suspend fun getAuthToken(): String? {
    val dataStoreKey = preferencesKey<String>(ACCESS_TOKEN)
    val preferences = dataStore.data.first()
    return preferences[dataStoreKey]
  }

  suspend fun saveAuthToken(accessToken: String) {
    Log.d("UserPreferences", "Saving Auth Token: $accessToken")

    val dataStoreKey = preferencesKey<String>(ACCESS_TOKEN)
    dataStore.edit { settings ->
      settings[dataStoreKey] = accessToken
    }
  }

  suspend fun clear() {
    dataStore.edit { preferences ->
      preferences.clear()
    }
  }

  companion object {
    private val ACCESS_TOKEN = "key_auth"

  }

}