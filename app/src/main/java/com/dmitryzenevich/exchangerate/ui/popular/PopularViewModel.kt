package com.dmitryzenevich.exchangerate.ui.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitryzenevich.exchangerate.domain.usecase.*
import com.dmitryzenevich.exchangerate.ui.adapter.ListState
import com.dmitryzenevich.exchangerate.ui.managers.AppPreferencesManager
import com.dmitryzenevich.exchangerate.ui.models.Rate
import com.dmitryzenevich.exchangerate.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val refreshExchangeRatesUseCase: RefreshExchangeRatesUseCase,
    private val getFavoriteRateByNameUseCase: GetFavoriteRateByNameUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val appPreferencesManager: AppPreferencesManager,
    getLatestUseCase: GetLatestUseCase
) : ViewModel() {
    private val _exchangeList = MutableStateFlow<ListState>(ListState.Clear)
    val exchangeList = _exchangeList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val rateNames = mutableListOf<String>()

    private val _selectedRateText = MutableStateFlow(appPreferencesManager.base)
    val selectedRateText = _selectedRateText.asStateFlow()

    val onItemSelected: (Int) -> Unit = { position ->
        appPreferencesManager.base = rateNames[position]
        _selectedRateText.value = rateNames[position]
    }

    init {
        getLatestUseCase.run()
            .onEach { rates ->
                val sortedRates = sortRates(rates)

                _exchangeList.updateList(
                    sortedRates.map { rate ->
                        CurrencyItemViewModel(
                            rate = rate,
                            getFavoriteRateByNameUseCase = getFavoriteRateByNameUseCase,
                            addToFavoritesCallback = ::addToFavorites,
                            removeFromFavoritesCallback = ::removeFromFavorites,
                            scope = viewModelScope
                        )
                    }
                )
                rateNames.clear()
                rateNames.addAll(rates.map { it.name })
            }
            .catch { Log.e(javaClass.simpleName, "Can't fetch local rates list", it) }
            .launchIn(viewModelScope)

        selectedRateText
            .onEach { getData() }
            .launchIn(viewModelScope)
    }

    fun onScreenResumed() {
        getData()
    }

    private fun sortRates(rates: List<Rate>): List<Rate> {
        return when (appPreferencesManager.sort) {
            SORT_ALPHABET_ASC -> rates.sortedBy { it.name }
            SORT_ALPHABET_DESC -> rates.sortedByDescending { it.name }
            SORT_RATE_ASC -> rates.sortedBy { it.rateValue.toDoubleOrNull() }
            SORT_RATE_DESC -> rates.sortedByDescending { it.rateValue.toDoubleOrNull() }
            else -> rates
        }
    }

    private fun getData() {
        refreshExchangeRatesUseCase.execute(appPreferencesManager.base)
            .onStart { _isLoading.value = true }
            .onCompletion { _isLoading.value = false }
            .retry(2)
            .catch { Log.e(javaClass.simpleName, "Can't refresh rates list", it) }
            .launchIn(viewModelScope)
    }

    private fun addToFavorites(rate: Rate) {
        addToFavoritesUseCase.execute(rate)
            .catch { Log.e(javaClass.simpleName, "Can't add rate to favorites: ${rate.name}", it) }
            .launchIn(viewModelScope)
    }

    private fun removeFromFavorites(rate: Rate) {
        removeFromFavoritesUseCase.execute(rate)
            .catch { Log.e(javaClass.simpleName, "Can't remove rate from favorites ${rate.name}", it) }
            .launchIn(viewModelScope)
    }
}
