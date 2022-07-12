package com.example.pokemontestproject.features.pokemon.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.FragmentPokemonBinding
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.Companion.PAGE_SIZE
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.ViewState.Success.Action
import com.example.pokemontestproject.features.pokemon.presentation.adapter.PaginationScrollListener
import com.example.pokemontestproject.features.pokemon.presentation.adapter.PokemonListDelegationAdapter
import com.example.pokemontestproject.features.pokemon.presentation.adapter.loaderAdapterDelegate
import com.example.pokemontestproject.features.pokemon.presentation.adapter.pokemonAdapterDelegate
import com.example.pokemontestproject.features.pokemon_detail.presentation.PokemonDetailFragment.Companion.ARG_POKEMON_DETAIL
import com.example.pokemontestproject.getAppComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val pokemonViewModel by viewModels<PokemonViewModel> { viewModelFactory }
    private val binding: FragmentPokemonBinding by viewBinding()
    private val pokemonAdapter = PokemonListDelegationAdapter(
        pokemonAdapterDelegate { idPokemon ->
            navigateToPokemonDetail(idPokemon = idPokemon)
        },
        loaderAdapterDelegate()
    )

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false

    override fun onAttach(context: Context) {
        getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        collectFlow()
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch() {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonViewModel.viewStateStateFlow.collect { viewState ->
                    when (viewState) {
                        is PokemonViewModel.ViewState.Success -> {
                            setIsEnableView(isEnable = true)
                            binding.refreshLayout.isRefreshing = false
                            pokemonAdapter.items = viewState.data
                            if (viewState.action == Action.START_POSITION) {
                                binding.recyclerView.smoothScrollToPosition(0)
                            }
                            isLoading = false
                            isLastPage = viewState.data.size < PAGE_SIZE
                            binding.randomListButton.isEnabled = true
                        }
                        PokemonViewModel.ViewState.Loading -> {
                            binding.refreshLayout.isRefreshing = true
                        }
                        is PokemonViewModel.ViewState.Error -> {
                            binding.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(context, viewState.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun initUi() {
        binding.recyclerView.apply {
            adapter = pokemonAdapter
            val paginationScrollListener = object :
                PaginationScrollListener(layoutManager as? LinearLayoutManager) {
                override fun loadMoreItems() {
                    this@PokemonFragment.isLoading = true
                    pokemonViewModel.loadNextPage()
                }

                override val isLastPage: Boolean get() = this@PokemonFragment.isLastPage
                override val isLoading: Boolean get() = this@PokemonFragment.isLoading
            }
            addOnScrollListener(paginationScrollListener)
        }
        initListeners()
    }

    private fun setIsEnableView(isEnable: Boolean) = with(binding) {
        hpCheckBox.isEnabled = isEnable
        attackCheckBox.isEnabled = isEnable
        defenseCheckBox.isEnabled = isEnable
        searchRadioButton.isEnabled = isEnable
        sortRadioButton.isEnabled = isEnable
    }

    private fun initListeners() = with(binding) {
        listOf(attackCheckBox, defenseCheckBox, hpCheckBox).forEach {
            it.setOnCheckedChangeListener { checkBoxView, isChecked ->
                pokemonViewModel.changeSpecifications(
                    checkBoxId = checkBoxView.id,
                    isChecked = isChecked
                )
            }
        }
        listOf(searchRadioButton, sortRadioButton).forEach {
            it.setOnCheckedChangeListener { radioButtonView, isChecked ->
                if (isChecked) {
                    pokemonViewModel.changeTypeMapping(radioButtonId = radioButtonView.id)
                }
            }
        }
        tryAgainButton.setOnClickListener {
            binding.errorLayout.visibility = View.INVISIBLE
            pokemonViewModel.loadCurrentPage()
        }
        randomListButton.setOnClickListener {
            binding.refreshLayout.isRefreshing = true
            pokemonViewModel.loadRandomPage()
        }
    }

    private fun navigateToPokemonDetail(idPokemon: Int) {
        val pokemonDetail = pokemonViewModel.getPokemonById(idPokemon = idPokemon)
        findNavController().navigate(
            R.id.action_pokemon_fragment_to_pokemon_detail_fragment,
            bundleOf(ARG_POKEMON_DETAIL to pokemonDetail)
        )
    }
}