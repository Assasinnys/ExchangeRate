package com.dmitryzenevich.exchangerate.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitryzenevich.exchangerate.domain.usecase.GetAllFavoriteRatesFlowUseCase
import com.dmitryzenevich.exchangerate.domain.usecase.RefreshFavoritesUseCase
import com.dmitryzenevich.exchangerate.domain.usecase.RemoveFromFavoritesUseCase
import com.dmitryzenevich.exchangerate.ui.adapter.ListState
import com.dmitryzenevich.exchangerate.ui.managers.AppPreferencesManager
import com.dmitryzenevich.exchangerate.ui.models.Rate
import com.dmitryzenevich.exchangerate.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getAllFavoriteRatesFlowUseCase: GetAllFavoriteRatesFlowUseCase,
    private val refreshFavoritesUseCase: RefreshFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val appPreferencesManager: AppPreferencesManager
) : ViewModel() {

    private val _favoriteList = MutableStateFlow<ListState>(ListState.Clear)
    val favoriteList = _favoriteList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllFavoriteRatesFlowUseCase.run()
            .onEach { rates ->
                val sortedRates = sortRates(rates)
                _favoriteList.updateList(
                    sortedRates.map { rate ->
                        FavoriteCurrencyItemViewModel(
                            rate = rate,
                            removeFromFavoritesCallback = ::removeFromFavorites
                        )
                    }
                )
            }
            .catch { Log.e(javaClass.simpleName, "Can't fetch favorite rates", it) }
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
        refreshFavoritesUseCase.execute(appPreferencesManager.base)
            .onStart { _isLoading.value = true }
            .onCompletion { _isLoading.value = false }
            .retry(2)
            .catch { Log.e(javaClass.simpleName, "Can't refresh") }
            .launchIn(viewModelScope)
    }

    private fun removeFromFavorites(rate: Rate) {
        removeFromFavoritesUseCase.execute(rate)
            .catch { Log.e(javaClass.simpleName, "Can't remove rate from favorites ${rate.name}", it) }
            .launchIn(viewModelScope)
    }
}