package com.dmitryzenevich.exchangerate.data.network

import com.dmitryzenevich.exchangerate.data.network.models.RemoteRateList
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    @GET("latest")
    suspend fun getLatestRates(@Query("base") base: String? = null): RemoteRateList
}