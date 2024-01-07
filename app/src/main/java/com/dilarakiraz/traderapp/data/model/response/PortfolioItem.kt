package com.dilarakiraz.traderapp.data.model.response

import com.google.gson.annotations.SerializedName

data class PortfolioItem(
    @SerializedName("Symbol") val symbol: String,
    @SerializedName("Qty_T2") val quantity: Int,
    @SerializedName("LastPx") val lastPrice: Double,
    var totalAmount: Double? = null
)
