package com.dilarakiraz.traderapp.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Result") val result: Result?,
    @SerializedName("AccountList") val accountList: List<String>?,
    @SerializedName("DefaultAccount") val defaultAccount: String?,
    @SerializedName("CustomerID") val customerID: String?,
)

data class Result(
    @SerializedName("State") val state: Boolean?,
    @SerializedName("Code") val code: Int?,
    @SerializedName("Description") val description: String?,
)
