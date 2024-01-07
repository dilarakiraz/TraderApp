package com.dilarakiraz.traderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilarakiraz.traderapp.common.Resource
import com.dilarakiraz.traderapp.data.model.response.PortfolioItem
import com.dilarakiraz.traderapp.databinding.ActivityPortfolioBinding
import com.dilarakiraz.traderapp.ui.login.LoginViewModel
import com.dilarakiraz.traderapp.ui.portfolio.PortfolioAdapter
import com.dilarakiraz.traderapp.ui.portfolio.PortfolioViewModel

class PortfolioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioBinding
    private val viewModel: PortfolioViewModel by viewModels()

    private val portfolioAdapter = PortfolioAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accountId = intent.getStringExtra("account_id")

        viewModel.getPortfolio(accountId ?: "")

        val recyclerViewPortfolio: RecyclerView = findViewById(R.id.recyclerViewPortfolio)
        val textViewTotalAmount: TextView = findViewById(R.id.totalAmountTextView)


        recyclerViewPortfolio.layoutManager = LinearLayoutManager(this)
        recyclerViewPortfolio.adapter = portfolioAdapter

        viewModel.portfolioResult.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    resource.data?.let { portfolioResponse ->
                        for (item in portfolioResponse.items.orEmpty()) {
                            Log.d("PortfolioItem", "Symbol: ${item.symbol}, Qty_T2: ${item.quantity}, LastPx: ${item.lastPrice}")
                        }

                        updatePortfolioItems(portfolioResponse.items.orEmpty())
                        updateTotalAmount(portfolioResponse.totalAmount, textViewTotalAmount)
                    }
                }

                is Resource.Error -> {
                    Log.e("PortfolioError", "Error: ${resource.message}")
                }

                else -> {}
            }
        }
    }

    private fun updatePortfolioItems(portfolioItems: List<PortfolioItem>) {
        portfolioAdapter.updateData(portfolioItems)
    }

    private fun updateTotalAmount(totalAmount: Double?, textView: TextView) {
        textView.text = "Toplam Tutar: ${totalAmount ?: 0.0}"
    }
}