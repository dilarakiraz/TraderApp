package com.dilarakiraz.traderapp.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilarakiraz.traderapp.common.Resource
import com.dilarakiraz.traderapp.data.model.response.PortfolioItem
import com.dilarakiraz.traderapp.data.model.response.PortfolioResponse
import com.dilarakiraz.traderapp.data.source.ApiService
import kotlinx.coroutines.launch

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

class PortfolioViewModel (private val apiService: ApiService) : ViewModel(){

    private val _portfolioResult = MutableLiveData<Resource<PortfolioResponse>>()
    val portfolioResult: LiveData<Resource<PortfolioResponse>> get() = _portfolioResult

    private val _portfolioItemList = MutableLiveData<List<PortfolioItem>>()
    val portfolioItemList: LiveData<List<PortfolioItem>> get() = _portfolioItemList

    private var portfolioAdapter: PortfolioAdapter? = PortfolioAdapter(emptyList())

    fun getPortfolio(accountNumber: String) {
        viewModelScope.launch {
            try {
                _portfolioResult.value = Resource.Loading()
                val portfolioResponse = apiService.getPortfolio(accountID = accountNumber)

                if (portfolioResponse.isSuccessful) {
                    portfolioResponse.body()?.let { portfolio ->
                        if (portfolio.state) {
                            _portfolioResult.value = Resource.Success(portfolio)

                            val portfolioItemList: List<PortfolioItem> = portfolio.items ?: emptyList()

                            updateAmountCalculations(portfolioItemList)

                            portfolioAdapter?.updateData(portfolioItemList)
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
    private fun updateAmountCalculations(portfolioItemList: List<PortfolioItem>) {
        portfolioItemList.forEach { item ->
            item.totalAmount = item.quantity * item.lastPrice
        }
    }
}