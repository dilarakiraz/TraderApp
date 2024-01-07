package com.dilarakiraz.traderapp.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

data class PortfolioResponse(
    @SerializedName("State")
    val state: Boolean, // API'den dönen başarı durumu

    @SerializedName("Description")
    val description: String?, // API'den dönen açıklama

    @SerializedName("Items")
    val items: List<PortfolioItem>?,

    @SerializedName("TotalAmount")
    val totalAmount: Double?
)