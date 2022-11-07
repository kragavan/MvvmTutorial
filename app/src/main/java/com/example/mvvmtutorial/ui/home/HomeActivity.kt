package com.example.mvvmtutorial.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmtutorial.R

/**
 * Holds logged-in user experience.
 */
class HomeActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
  }
}