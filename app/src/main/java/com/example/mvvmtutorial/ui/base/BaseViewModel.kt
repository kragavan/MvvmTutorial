package com.example.mvvmtutorial.ui.base

import androidx.lifecycle.ViewModel
import com.example.mvvmtutorial.data.network.UserApi
import com.example.mvvmtutorial.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(private val repository:BaseRepository): ViewModel() {

  suspend fun logout(api : UserApi) = repository.logout(api)

}