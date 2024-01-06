package com.dilarakiraz.traderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dilarakiraz.traderapp.common.Resource
import com.dilarakiraz.traderapp.common.showToast
import com.dilarakiraz.traderapp.data.source.ApiService
import com.dilarakiraz.traderapp.databinding.ActivityLoginBinding
import com.dilarakiraz.traderapp.di.RetrofitBuilder
import com.dilarakiraz.traderapp.ui.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
        viewModel = LoginViewModel(apiService)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.login(username, password)
                observeLoginResult()
            } else {
                showToast(getString(R.string.empty_credentials_error))
            }
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    val accountNumber = resource.data?.defaultAccount
                    if (!accountNumber.isNullOrBlank()) {
                        showToast(getString(R.string.successful_login))
                        val intent = Intent(this@LoginActivity, PortfolioActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast(getString(R.string.account_number_error))
                    }
                }

                is Resource.Error -> {
                    showToast(getString(R.string.login_failed_error, resource.message))
                }
            }
        }
    }
}