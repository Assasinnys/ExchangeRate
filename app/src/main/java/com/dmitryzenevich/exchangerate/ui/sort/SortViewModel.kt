package com.dmitryzenevich.exchangerate.ui.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitryzenevich.exchangerate.ui.managers.AppPreferencesManager
import com.dmitryzenevich.exchangerate.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager
) : ViewModel() {

    val isAlphabetAsc = MutableStateFlow(false)
    val isAlphabetDesc = MutableStateFlow(false)
    val isRateAsc = MutableStateFlow(false)
    val isRateDesc = MutableStateFlow(false)

    init {
        isAlphabetAsc
            .onEach { if (it) appPreferencesManager.sort = SORT_ALPHABET_ASC }
            .launchIn(viewModelScope)
        isAlphabetDesc
            .onEach { if (it) appPreferencesManager.sort = SORT_ALPHABET_DESC }
            .launchIn(viewModelScope)
        isRateAsc
            .onEach { if (it) appPreferencesManager.sort = SORT_RATE_ASC }
            .launchIn(viewModelScope)
        isRateDesc
            .onEach { if (it) appPreferencesManager.sort = SORT_RATE_DESC }
            .launchIn(viewModelScope)

        when(appPreferencesManager.sort) {
            SORT_ALPHABET_ASC -> isAlphabetAsc.value = true
            SORT_ALPHABET_DESC -> isAlphabetDesc.value = true
            SORT_RATE_ASC -> isRateAsc.value = true
            SORT_RATE_DESC -> isRateDesc.value = true
        }
    }
}