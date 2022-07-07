package com.example.pokemontestproject.features.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToListItemModel
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToPresentation
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonPresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewStateFlow.asStateFlow()
    private val pokemonList = mutableListOf<PokemonPresentationModel>()

    init {
        getPokemon()
    }

    fun getPokemon() {
        viewModelScope.launch {
            getPokemonListUseCase(30, 0).map {
                it.map { pokemon ->
                    getPokemonDetailUseCase(pokemon.name).mapToPresentation()
                }
            }.catch {
                _viewStateFlow.value = ViewState.Error(message = it.message.toString())
            }.collect { newPokemonList ->
                pokemonList.addAll(newPokemonList)
                _viewStateFlow.value =
                    ViewState.Success(data = newPokemonList.map { it.mapToListItemModel() })
            }
        }
    }

    sealed class ViewState {
        class Success(val data: List<PokemonListItemPresentationModel>) : ViewState()
        class Error(val message: String) : ViewState()
        object Loading : ViewState()
    }
}