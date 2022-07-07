package com.example.pokemontestproject.features.pokemon.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.FragmentPokemonBinding
import com.example.pokemontestproject.features.pokemon.presentation.adapter.PokemonAdapter
import com.example.pokemontestproject.getAppComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val pokemonViewModel by viewModels<PokemonViewModel> { viewModelFactory }
    private val binding: FragmentPokemonBinding by viewBinding()
    private val adapter = PokemonAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonViewModel.viewState.collect { viewState ->
                    when (viewState) {
                        is PokemonViewModel.ViewState.Error -> {

                        }
                        PokemonViewModel.ViewState.Loading -> {
                            showOrHideLoading(true)
                        }
                        is PokemonViewModel.ViewState.Success -> {
                            adapter.setData(viewState.data)
                            showOrHideLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showOrHideLoading(isVisibility: Boolean) {
        binding.loadingLayout.visibility = if (isVisibility) View.VISIBLE else View.GONE
    }

    private fun initUi() {
        binding.recyclerView.adapter = adapter
    }

    override fun onAttach(context: Context) {
        getAppComponent().inject(this)
        super.onAttach(context)
    }
}