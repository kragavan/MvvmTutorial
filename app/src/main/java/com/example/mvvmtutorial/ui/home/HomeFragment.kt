package com.example.mvvmtutorial.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvmtutorial.data.network.Resource
import com.example.mvvmtutorial.data.network.UserApi
import com.example.mvvmtutorial.data.repository.UserRepository
import com.example.mvvmtutorial.data.responses.User
import com.example.mvvmtutorial.databinding.FragmentHomeBinding
import com.example.mvvmtutorial.ui.base.BaseFragment
import com.example.mvvmtutorial.ui.visible
import kotlinx.coroutines.runBlocking

/**
 * Fragment managing Logged-in user experience.
 * @param HomeViewModel ViewModel class to be instantiated
 * @param FragmentHomeBinding ViewBinding of [HomeFragment]
 * @param UserRepository Repository class
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.progressbar.visible(true)
    viewModel.getUser()

    viewModel.user.observe(viewLifecycleOwner, Observer {
      binding.progressbar.visible(false)
      when(it) {
        is Resource.Success -> {
          updateUI(it.value.user)
        }
      }

    })

    binding.buttonLogout.setOnClickListener {
      logout()
    }
  }

  private fun updateUI(user: User) {
    with(binding) {
      textViewId.text = user.id.toString()
      textViewName.text = user.name
      textViewEmail.text = user.email

    }
  }

  override fun getViewModel(): Class<HomeViewModel> {
    return HomeViewModel::class.java
  }

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ) = FragmentHomeBinding.inflate(inflater, container, false)

  override fun getFragmentRepository(): UserRepository {
    val authToken = runBlocking { userPreferences.getAuthToken() }
    val userApi = remoteDataSource.buildApi(UserApi::class.java, authToken)
    return UserRepository(userApi)
  }
}