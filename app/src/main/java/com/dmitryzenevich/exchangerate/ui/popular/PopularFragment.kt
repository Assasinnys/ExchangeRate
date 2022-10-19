package com.dmitryzenevich.exchangerate.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dmitryzenevich.exchangerate.BR
import com.dmitryzenevich.exchangerate.R
import com.dmitryzenevich.exchangerate.databinding.FragmentPopularBinding
import com.dmitryzenevich.exchangerate.ui.adapter.LastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private val popularViewModel by viewModels<PopularViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPopularBinding.inflate(inflater, container, false)
        binding.viewModel = popularViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = LastAdapter(BR.viewModel, isUsingFlow = true, viewLifecycleOwner)
            .map<CurrencyItemViewModel>(R.layout.item_currency)
            .into(binding.currencyList)

        popularViewModel.exchangeList
            .onEach { adapter.updateListState(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        popularViewModel.onScreenResumed()
    }
}
