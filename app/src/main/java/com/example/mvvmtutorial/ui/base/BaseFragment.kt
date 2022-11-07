package com.example.mvvmtutorial.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.mvvmtutorial.data.UserPreferences
import com.example.mvvmtutorial.data.network.RemoteDataSource
import com.example.mvvmtutorial.data.network.UserApi
import com.example.mvvmtutorial.data.repository.BaseRepository
import com.example.mvvmtutorial.ui.auth.AuthActivity
import com.example.mvvmtutorial.ui.startNewActivity
import kotlinx.coroutines.launch

/**
 * Base Fragment class for all fragments
 *
 * @param VM ViewModel class to be instantiated
 * @param B ViewBinding class to be instantiated
 * @param R Repository class for specific flows
 */
abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepository> : Fragment() {

  protected lateinit var userPreferences: UserPreferences
  protected lateinit var binding: B
  protected val remoteDataSource = RemoteDataSource()
  protected lateinit var viewModel: VM

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    userPreferences = UserPreferences(requireContext())
    binding = getFragmentBinding(inflater, container)
    val factory = ViewModelFactory(getFragmentRepository())
    viewModel = ViewModelProvider(this, factory).get(getViewModel())
    lifecycleScope.launch {
      userPreferences.getAuthToken()?.first()
    }
    return binding.root

  }

  fun logout() {
    lifecycleScope.launch {
      val authToken = userPreferences.getAuthToken()
      val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
      viewModel.logout(api)
      userPreferences.clear()
      requireActivity().startNewActivity(AuthActivity::class.java)
    }
  }

  abstract fun getViewModel() : Class<VM>

  abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : B

  abstract fun getFragmentRepository() : R

}