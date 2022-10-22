package com.dmitryzenevich.exchangerate.domain.mappers

import com.dmitryzenevich.exchangerate.data.database.entities.LocalFavoriteRate
import com.dmitryzenevich.exchangerate.data.database.entities.LocalRate
import com.dmitryzenevich.exchangerate.data.network.models.RemoteRateList
import com.dmitryzenevich.exchangerate.ui.models.Rate

fun RemoteRateList.toLocalRates() = rates.map {
    LocalRate(it.key, it.value)
}

fun RemoteRateList.toLocalFavoriteRates() = rates.map {
    LocalFavoriteRate(it.key, it.value)
}

fun LocalRate.toRate() = Rate(
    name = rateName,
    rateValue = rateValue
)

fun Rate.toLocalFavoriteRate() = LocalFavoriteRate(
    rateValue = rateValue,
    rateName = name
)

fun LocalFavoriteRate.toRate() = Rate(
    name = rateName,
    rateValue = rateValue
)