package com.dmitryzenevich.exchangerate.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dmitryzenevich.exchangerate.databinding.FragmentSortBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : Fragment() {

    private val sortViewModel by viewModels<SortViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSortBinding.inflate(inflater, container, false)
        binding.viewModel = sortViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}