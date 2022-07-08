package com.example.pokemontestproject.features.pokemon.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.FragmentPokemonBinding
import com.example.pokemontestproject.features.pokemon.presentation.adapter.FooterLoadStateAdapter
import com.example.pokemontestproject.features.pokemon.presentation.adapter.PokemonPagingAdapter
import com.example.pokemontestproject.getAppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val pokemonViewModel by viewModels<PokemonViewModel> { viewModelFactory }
    private val binding: FragmentPokemonBinding by viewBinding()
    private val adapter = PokemonPagingAdapter()

    override fun onAttach(context: Context) {
        getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        collectPagingDataFlow()
    }

    private fun collectPagingDataFlow() {
        viewLifecycleOwner.lifecycleScope.launch() {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonViewModel.pokemonPagingDataStateFlow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initUi() {
        binding.recyclerView.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadState ->
                    binding.loadingLayout.isVisible = loadState.refresh is LoadState.Loading
                    binding.errorLayout.isVisible = loadState.refresh is LoadState.Error
                }
            }
        }
        binding.tryAgainButton.setOnClickListener {

        }
    }
}