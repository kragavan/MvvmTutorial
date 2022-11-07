package com.example.mvvmtutorial.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mvvmtutorial.data.network.Resource
import com.example.mvvmtutorial.ui.auth.LoginFragment
import com.example.mvvmtutorial.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

/**
 * Start new [Activity] using the provided [Class]
 *
 * @param A Activity class
 * @param activity Provide [Activity] to be started
 */
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
  val intent = Intent(this, activity).also {
    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(it)
  }
}

/**
 * Enable/disable visibility of View objects
 *
 * @param isVisible
 */
fun View.visible(isVisible: Boolean) {
  visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
  isEnabled = enabled
  alpha = if(enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
  val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT )
  action?.let {
    snackbar.setAction("Retry") {
      it()
    }
  }
  snackbar.show()
}

/**
 * Handle API error scenarios
 *
 * @param failure [Resource.Failure] object
 * @param retry Provide Lambda retry function
 */
fun Fragment.handleApiError(
  failure: Resource.Failure,
  retry: (() -> Unit)? = null
  ) {

  when {
    failure.isNetworkError -> {
      requireView().snackbar("Internet connection error", retry)
    }
    failure.errorCode == 401 -> {
      if(this is LoginFragment) {
        requireView().snackbar("Invalid username or password") 
      } else {
        (this as BaseFragment<*,*,*>).logout()
      }
    }
    else -> {
      val error = failure.errorBody?.string().toString()
      requireView().snackbar(error)

    }
  }
}