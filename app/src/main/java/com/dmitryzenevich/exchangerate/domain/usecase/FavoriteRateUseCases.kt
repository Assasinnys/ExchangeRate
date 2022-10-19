package com.dmitryzenevich.exchangerate.domain.usecase

import com.dmitryzenevich.exchangerate.domain.repository.ExchangeRatesRepository
import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : BaseUseCase<Unit, Rate>() {

    override suspend fun run(params: Rate) {
        exchangeRatesRepository.addToFavorites(params)
    }
}

class RemoveFromFavoritesUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : BaseUseCase<Unit, Rate>() {

    override suspend fun run(params: Rate) {
        exchangeRatesRepository.removeFromFavorites(params)
    }
}

class GetFavoriteRateByNameUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) {
    fun run(name: String): Flow<Rate?> {
        return exchangeRatesRepository.getFavoriteRateByNameFlow(name)
    }
}

class GetAllFavoriteRatesFlowUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) {
    fun run() = exchangeRatesRepository.getFavoriteRates()
}

class RefreshFavoritesUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : BaseUseCase<Unit, String>() {

    override suspend fun run(params: String) {
        exchangeRatesRepository.refreshFavoriteRates(params)
    }
}
