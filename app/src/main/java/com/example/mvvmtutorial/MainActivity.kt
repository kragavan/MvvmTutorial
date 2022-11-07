package com.example.mvvmtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.mvvmtutorial.data.UserPreferences
import com.example.mvvmtutorial.ui.auth.AuthActivity
import com.example.mvvmtutorial.ui.home.HomeActivity
import com.example.mvvmtutorial.ui.startNewActivity
import kotlinx.coroutines.launch

/**
 * Entry point of Application. Determines user navigation based on availability of Auth Token.
 */
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val userPreferences = UserPreferences(this)
    Log.d("Sample", "Loading Auth Token")

    lifecycleScope.launch {
      val authToken = userPreferences.getAuthToken()
      val activity = if(authToken == null) AuthActivity::class.java else HomeActivity::class.java
      startNewActivity(activity)
    }

  }
}