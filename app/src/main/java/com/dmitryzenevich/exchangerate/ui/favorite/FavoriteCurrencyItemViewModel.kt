package com.dmitryzenevich.exchangerate.ui.favorite

import com.dmitryzenevich.exchangerate.ui.adapter.RecyclerViewItem
import com.dmitryzenevich.exchangerate.ui.models.Rate
import kotlinx.coroutines.flow.*

class FavoriteCurrencyItemViewModel(
    private val rate: Rate,
    private val removeFromFavoritesCallback: (Rate) -> Unit
) : RecyclerViewItem {

    override val stableId: Long = rate.hashCode().toLong()

    private val _currencyName = MutableStateFlow(rate.name)
    val currencyName = _currencyName.asStateFlow()

    private val _currencyValue = MutableStateFlow(rate.rateValue)
    val currencyValue = _currencyValue.asStateFlow()

    fun onStarClick() {
        removeFromFavoritesCallback.invoke(rate)
    }
}