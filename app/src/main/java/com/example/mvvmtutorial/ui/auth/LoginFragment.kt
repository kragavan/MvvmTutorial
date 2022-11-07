package com.example.mvvmtutorial.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmtutorial.databinding.FragmentLoginBinding
import com.example.mvvmtutorial.data.network.AuthApi
import com.example.mvvmtutorial.data.network.Resource
import com.example.mvvmtutorial.data.repository.AuthRepository
import com.example.mvvmtutorial.ui.base.BaseFragment
import com.example.mvvmtutorial.ui.enable
import com.example.mvvmtutorial.ui.handleApiError
import com.example.mvvmtutorial.ui.home.HomeActivity
import com.example.mvvmtutorial.ui.startNewActivity
import com.example.mvvmtutorial.ui.visible
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    binding.progressbar.visible(false)
    binding.buttonLogin.enable(false)

    viewModel.loginResponse.observe(viewLifecycleOwner) {
      binding.progressbar.visible(false)
      when(it) {
        is Resource.Success -> {
          Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()

          lifecycleScope.launch {
            viewModel.saveAuthToken(it.value.user.access_token.toString())
            requireActivity().startNewActivity(HomeActivity::class.java)
          }
        }
        is Resource.Failure -> {
          handleApiError(it) { login() }
        }
      }
    }

    binding.editTextTextPassword.addTextChangedListener {
      val email = binding.editTextTextEmailAddress.toString().trim()
      binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
    }

    binding.buttonLogin.setOnClickListener {
      login()

    }
  }

  private fun login() {
    val email = binding.editTextTextEmailAddress.text.toString().trim()
    val password = binding.editTextTextPassword.text.toString().trim()
    binding.progressbar.visible(true)
    viewModel.login(email, password)
  }

  override fun getViewModel(): Class<AuthViewModel> {
    return AuthViewModel::class.java
  }

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ) = FragmentLoginBinding.inflate(inflater, container, false)

  override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}