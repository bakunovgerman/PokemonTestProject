package com.example.pokemontestproject.features.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToListItemModel
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToPresentation
import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem
import com.example.pokemontestproject.features.pokemon.presentation.models.LoaderModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _viewStateStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewStateStateFlow = _viewStateStateFlow.asStateFlow()
    private var page = 0
    private val pokemonList = mutableListOf<ListItem>()

    init {
        getPokemonList(page)
    }

    private fun getPokemonList(offset: Int) {
        viewModelScope.launch {
            getPokemonListUseCase(limit = PAGE_SIZE, offset = offset)
                .catch { t ->
                    _viewStateStateFlow.value = ViewState.Error(message = t.message.toString())
                }
                .collect { newPokemonList ->
                    pokemonList.addAll(newPokemonList.map {
                        it.mapToPresentation().mapToListItemModel()
                    })
                    _viewStateStateFlow.value = ViewState.Success(
                        data = pokemonList.toMutableList().apply {
                            // если страницы не закончились, то добавляем вниз лоудер
                            if (this.size >= PAGE_SIZE) {
                                add(LoaderModel(0))
                            }
                        })
                }
        }
    }

    fun loadNextPage() = getPokemonList (offset = ++page * PAGE_SIZE)

    fun loadCurrentPage() = getPokemonList(offset = page * PAGE_SIZE)

    class MappingPokemonListState(
        val specifications: Set<Specifications>,
        val typeMapping: TypeMapping
    ) {
        enum class Specifications {
            ATTACK, DEFENSE, HP
        }

        enum class TypeMapping {
            SEARCH, FILTER
        }
    }

    sealed class ViewState {
        class Success(val data: List<ListItem>) : ViewState()
        object Loading : ViewState()
        class Error(message: String) : ViewState()
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}