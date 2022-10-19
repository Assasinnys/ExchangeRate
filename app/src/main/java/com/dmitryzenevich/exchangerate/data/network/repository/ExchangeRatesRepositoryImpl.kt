package com.dmitryzenevich.exchangerate.data.network.repository

import com.dmitryzenevich.exchangerate.data.database.dao.FavoritesDao
import com.dmitryzenevich.exchangerate.data.database.dao.RatesDao
import com.dmitryzenevich.exchangerate.data.network.ExchangeRateApi
import com.dmitryzenevich.exchangerate.domain.mappers.*
import com.dmitryzenevich.exchangerate.domain.repository.ExchangeRatesRepository
import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(
    private val remoteSource: ExchangeRateApi,
    private val ratesDao: RatesDao,
    private val favoritesDao: FavoritesDao
) : ExchangeRatesRepository {

    override suspend fun refreshLatestRates(base: String) {
        val rates = remoteSource.getLatestRates(base).rates.toLocalRates()
        ratesDao.insertAll(rates)
    }

    override fun getLastRates(): Flow<List<Rate>> {
        return ratesDao.getRates().map { localRates ->
            localRates.map { it.toRate() }
        }
    }

    override suspend fun addToFavorites(currency: Rate) {
        favoritesDao.insertFavoriteRate(currency.toLocalFavoriteRate())
    }

    override suspend fun removeFromFavorites(currency: Rate) {
        favoritesDao.deleteFavoriteRate(currency.toLocalFavoriteRate())
    }

    override fun getFavoriteRateByNameFlow(name: String): Flow<Rate?> {
        return favoritesDao.getFavoriteRateByName(name).map {
            it?.toRate()
        }
    }

    override suspend fun refreshFavoriteRates(base: String) {
        val rates = remoteSource.getLatestRates(base).rates.toLocalFavoriteRates()
        val favoriteRates = favoritesDao.getFavoriteRates()
        val favoritesForUpdate = rates.filter { fetchedRates ->
            favoriteRates.find { fetchedRates.rateName == it.rateName } != null
        }
        favoritesDao.refreshFavoriteRates(favoritesForUpdate)
    }

    override fun getFavoriteRates(): Flow<List<Rate>> {
        return favoritesDao.getFavoriteRatesFlow().map { localFavorite ->
            localFavorite.map { it.toRate() }
        }
    }
}
