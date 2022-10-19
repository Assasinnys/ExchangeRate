package com.dmitryzenevich.exchangerate.domain.usecase

import com.dmitryzenevich.exchangerate.domain.repository.ExchangeRatesRepository
import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) {
    fun run(): Flow<List<Rate>> {
        return exchangeRatesRepository.getLastRates()
    }
}

class RefreshExchangeRatesUseCase @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : BaseUseCase<Unit, String>() {

    override suspend fun run(params: String) {
        exchangeRatesRepository.refreshLatestRates(params)
    }
}
