package com.dilarakiraz.traderapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilarakiraz.traderapp.common.Resource
import com.dilarakiraz.traderapp.data.model.response.LoginResponse
import com.dilarakiraz.traderapp.data.source.ApiService
import kotlinx.coroutines.launch

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<LoginResponse>>()
    val loginResult: LiveData<Resource<LoginResponse>> get() = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loginResult.value = Resource.Loading()
                val response = apiService.login(username = username, password = password)
                if (response.isSuccessful) {
                    _loginResult.value = Resource.Success(response.body())
                } else {
                    _loginResult.value = Resource.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginResult.value = Resource.Error("An error occurred: ${e.message}")
            }
        }
    }
}