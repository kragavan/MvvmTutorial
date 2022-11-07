package com.example.mvvmtutorial.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmtutorial.R

/**
 * Provides User Authentication screens
 */
class AuthActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_auth)
  }
}