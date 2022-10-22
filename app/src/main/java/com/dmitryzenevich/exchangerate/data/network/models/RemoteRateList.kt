package com.dmitryzenevich.exchangerate.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRateList(
    @field:Json(name = "success")
    val isSuccess: Boolean,
    @field:Json(name = "timestamp")
    val timestamp: Long,
    @field:Json(name = "base")
    val base: String,
    @field:Json(name = "date")
    val date: String,
    @field:Json(name = "rates")
    val rates: Map<String, String>
)
