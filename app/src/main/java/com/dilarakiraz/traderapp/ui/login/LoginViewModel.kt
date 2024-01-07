package com.dilarakiraz.traderapp.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilarakiraz.traderapp.PortfolioActivity
import com.dilarakiraz.traderapp.common.Resource
import com.dilarakiraz.traderapp.data.model.response.LoginResponse
import com.dilarakiraz.traderapp.data.model.response.PortfolioResponse
import com.dilarakiraz.traderapp.data.source.ApiService
import kotlinx.coroutines.launch

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<LoginResponse>>()
    val loginResult: LiveData<Resource<LoginResponse>> get() = _loginResult

    private val _portfolioResult = MutableLiveData<Resource<PortfolioResponse>>()
    val portfolioResult: LiveData<Resource<PortfolioResponse>> get() = _portfolioResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loginResult.value = Resource.Loading()
                val response = apiService.login(username = username, password = password)

                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        if (loginResponse.result?.state == true) {
                            _loginResult.value = Resource.Success(loginResponse)

                            val accountNumber = loginResponse.defaultAccount

                            // Hesap numarasını kullanarak portföy ekranını aç
                            if (!accountNumber.isNullOrBlank()) {
                                getPortfolio(accountNumber)
                            } else {
                                _portfolioResult.value = Resource.Error("Hesap numarası boş.")
                            }
                        } else {
                            _loginResult.value =
                                Resource.Error("#Giriş başarısız. ${loginResponse.result?.description}")
                        }
                    }
                } else {
                    _loginResult.value = Resource.Error("Login request failed: ${response.message()}")
                    Log.e("LoginViewModel", "Login request failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginResult.value = Resource.Error("An error occurred: ${e.message}")
            }
        }
    }

    private fun getPortfolio(accountNumber: String) {
        // Hesap numarasını kullanarak portföy sorgusunu yap
        viewModelScope.launch {
            try {
                _portfolioResult.value = Resource.Loading()
                val portfolioResponse = apiService.getPortfolio(accountID = accountNumber)

                if (portfolioResponse.isSuccessful) {
                    portfolioResponse.body()?.let { portfolio ->
                        if (portfolio.state) {
                            _portfolioResult.value = Resource.Success(portfolio)
                        } else {
                            _portfolioResult.value =
                                Resource.Error("#Hesap özeti alınamadı. ${portfolio.description}")
                        }
                    }
                } else {
                    _portfolioResult.value =
                        Resource.Error("Hesap özeti alınamadı: ${portfolioResponse.message()}")
                }
            } catch (e: Exception) {
                _portfolioResult.value = Resource.Error("Bir hata oluştu: ${e.message}")
            }
        }
    }
}



