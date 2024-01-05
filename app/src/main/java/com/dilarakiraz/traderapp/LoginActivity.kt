package com.dilarakiraz.traderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dilarakiraz.traderapp.data.model.response.LoginResponse
import com.dilarakiraz.traderapp.data.source.ApiService
import com.dilarakiraz.traderapp.databinding.ActivityLoginBinding
import com.dilarakiraz.traderapp.di.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                login(username, password)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Kullanıcı adı ve şifre boş olamaz.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) {
        val loginResponse = response.body()

        if (loginResponse != null) {
            val result = loginResponse.result

            if (result?.state == true) {
                val accountNumber = loginResponse.defaultAccount

                if (!accountNumber.isNullOrBlank()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Giriş başarılı.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@LoginActivity, PortfolioActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Hesap numarası alınamadı.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val errorMessage = result?.description ?: "Giriş başarısız."
                Toast.makeText(
                    this@LoginActivity,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this@LoginActivity,
                "Sunucu tarafında bir hata oluştu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(username = username, password = password)
                Log.d("LoginActivity", "API çağrısı başarılı. Yanıt: ${response.body()}")
                withContext(Dispatchers.Main) {
                    handleLoginResponse(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Bir hata oluştu: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}