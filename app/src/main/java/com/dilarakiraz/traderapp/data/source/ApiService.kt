package com.dilarakiraz.traderapp.data.source

import com.dilarakiraz.traderapp.data.model.response.LoginResponse
import com.dilarakiraz.traderapp.data.model.response.PortfolioResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Integration.aspx")
    suspend fun login(
        @Query("MsgType") msgType: String = "A",
        @Query("Username") username: String,
        @Query("Password") password: String,
        @Query("AccountID") accountID: String= "0",
        @Query("ExchangeID") exchangeID: String = "4",
        @Query("OutputType") outputType: String = "2"
    ): Response<LoginResponse>

    @GET("Integration.aspx?MsgType=AN&ExchangeID=4&OutputType=2")
    suspend fun getPortfolio(
        @Query("AccountID") accountID: String
    ): Response<PortfolioResponse>
}