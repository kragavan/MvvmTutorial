package com.example.mvvmtutorial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtutorial.data.network.Resource
import com.example.mvvmtutorial.data.repository.UserRepository
import com.example.mvvmtutorial.data.responses.LoginResponse
import com.example.mvvmtutorial.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel controlling Logged-in user experience between [HomeFragment] and [UserRepository]
 *
 * @property repository [UserRepository] class to persist user information
 */
class HomeViewModel(
  private val repository: UserRepository
): BaseViewModel(repository) {

  private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
  val user: LiveData<Resource<LoginResponse>>
  get() = _user

  fun getUser() = viewModelScope.launch {
    _user.value = repository.getUser()
  }
}