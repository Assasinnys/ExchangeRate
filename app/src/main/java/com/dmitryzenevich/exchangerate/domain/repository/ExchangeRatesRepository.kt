package com.dmitryzenevich.exchangerate.domain.repository

import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun refreshLatestRates(base: String)
    fun getLastRates(): Flow<List<Rate>>
    suspend fun addToFavorites(currency: Rate)
    suspend fun removeFromFavorites(currency: Rate)
    fun getFavoriteRateByNameFlow(name: String): Flow<Rate?>
    suspend fun refreshFavoriteRates(base: String)
    fun getFavoriteRates(): Flow<List<Rate>>
}
