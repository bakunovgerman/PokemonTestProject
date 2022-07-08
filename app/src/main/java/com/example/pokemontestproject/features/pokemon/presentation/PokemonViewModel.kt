package com.example.pokemontestproject.features.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPagedPokemonUseCase
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToListItemModel
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToPresentation
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonViewModel @Inject constructor(getPagedPokemonUseCase: GetPagedPokemonUseCase) :
    ViewModel() {

    val pokemonPagingDataStateFlow: Flow<PagingData<PokemonListItemPresentationModel>> =
        getPagedPokemonUseCase(PAGE_SIZE).map { pageData ->
            pageData.map {
                it.mapToPresentation().mapToListItemModel()
            }
        }.cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 30
    }
}