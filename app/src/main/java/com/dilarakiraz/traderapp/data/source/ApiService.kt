package com.dilarakiraz.traderapp.data.source

import com.dilarakiraz.traderapp.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Integration.aspx")
    suspend fun login(
        @Query("MsgType") msgType: String = "A",
        @Query("CustomerNo") customerNo: String = "0",
        @Query("Username") username: String,
        @Query("Password") password: String,
        @Query("AccountID") accountID: String = "0",
        @Query("ExchangeID") exchangeID: String = "4",
        @Query("OutputType") outputType: String = "2"
    ): Response<LoginResponse>
}