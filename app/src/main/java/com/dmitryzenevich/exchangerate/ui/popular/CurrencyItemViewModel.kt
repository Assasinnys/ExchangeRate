package com.dmitryzenevich.exchangerate.ui.popular

import android.util.Log
import com.dmitryzenevich.exchangerate.domain.usecase.GetFavoriteRateByNameUseCase
import com.dmitryzenevich.exchangerate.ui.adapter.RecyclerViewItem
import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class CurrencyItemViewModel(
    private val rate: Rate,
    getFavoriteRateByNameUseCase: GetFavoriteRateByNameUseCase,
    private val addToFavoritesCallback: (Rate) -> Unit,
    private val removeFromFavoritesCallback: (Rate) -> Unit,
    scope: CoroutineScope
) : RecyclerViewItem {
    override val stableId: Long = rate.hashCode().toLong()

    private val _currencyName = MutableStateFlow(rate.name)
    val currencyName = _currencyName.asStateFlow()

    private val _currencyValue = MutableStateFlow(rate.rateValue)
    val currencyValue = _currencyValue.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        getFavoriteRateByNameUseCase.run(rate.name)
            .onEach { _isFavorite.value = it != null }
            .catch { Log.e(javaClass.simpleName, "Can't fetch favorite state ${rate.name}", it) }
            .launchIn(scope)
    }

    fun onStarClick() {
        _isFavorite.value = !_isFavorite.value

        if (_isFavorite.value) addToFavoritesCallback.invoke(rate)
        else removeFromFavoritesCallback.invoke(rate)
    }
}