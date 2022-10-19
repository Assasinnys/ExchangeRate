package com.dmitryzenevich.exchangerate.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dmitryzenevich.exchangerate.BR
import com.dmitryzenevich.exchangerate.R
import com.dmitryzenevich.exchangerate.databinding.FragmentFavoritesBinding
import com.dmitryzenevich.exchangerate.ui.adapter.LastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.viewModel = favoritesViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = LastAdapter(BR.viewModel, isUsingFlow = true, lifecycleOwner = viewLifecycleOwner)
            .map<FavoriteCurrencyItemViewModel>(R.layout.item_favorite_currency)
            .into(binding.currencyList)

        favoritesViewModel.favoriteList
            .onEach { adapter.updateListState(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.onScreenResumed()
    }
}