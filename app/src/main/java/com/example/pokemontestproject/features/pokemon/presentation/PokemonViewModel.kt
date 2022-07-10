package com.example.pokemontestproject.features.pokemon.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontestproject.R
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.MappingPokemonListState.Specifications
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.MappingPokemonListState.TypeMapping
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.ViewState.Success.Action
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToListItemModel
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToPresentation
import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem
import com.example.pokemontestproject.features.pokemon.presentation.models.LoaderModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
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
    private var mappingPokemonListState = MappingPokemonListState(
        specifications = mutableSetOf(),
        typeMapping = TypeMapping.SEARCH
    )
    private var page = 0
    private val pokemonList = mutableListOf<PokemonListItemPresentationModel>()

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
                    setSuccessData(mappingPokemonList())
                }
        }
    }

    fun loadNextPage() = getPokemonList(offset = ++page * PAGE_SIZE)

    fun loadCurrentPage() = getPokemonList(offset = page * PAGE_SIZE)

    private fun mappingPokemonList(): List<PokemonListItemPresentationModel> =
        with(mappingPokemonListState) {
            val mappedPokemonList = pokemonList
            if (specifications.isNotEmpty()) {
                when (typeMapping) {
                    TypeMapping.SEARCH -> {
                        return@with searchPokemon(mappedPokemonList)
                    }
                    TypeMapping.SORT -> {
                        return@with sortedPokemonList(mappedPokemonList).map { it.copy(isSelected = false) }
                    }
                }
            }
            return@with mappedPokemonList
        }

    private fun sortedPokemonList(
        mappedPokemonList: List<PokemonListItemPresentationModel>
    ): List<PokemonListItemPresentationModel> = with(mappingPokemonListState) {
        when {
            specifications.any { it == Specifications.ATTACK && it == Specifications.DEFENSE && it == Specifications.HP } -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.defense },
                        { it.hp })
                ).reversed()
            }
            specifications.any { it == Specifications.ATTACK && it == Specifications.DEFENSE } -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.defense })
                ).reversed()
            }
            specifications.any { it == Specifications.ATTACK && it == Specifications.HP } -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.hp })
                ).reversed()
            }
            specifications.any { it == Specifications.DEFENSE && it == Specifications.HP } -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.defense },
                        { it.hp })
                ).reversed()
            }
            specifications.contains(Specifications.ATTACK) -> {
                return mappedPokemonList.sortedBy { it.attack }.reversed()
            }
            specifications.contains(Specifications.DEFENSE) -> {
                return mappedPokemonList.sortedBy { it.defense }.reversed()
            }
            else -> {
                return mappedPokemonList.sortedBy { it.hp }.reversed()
            }
        }
    }

    private fun searchPokemon(mappingPokemonList: MutableList<PokemonListItemPresentationModel>): List<PokemonListItemPresentationModel> =
        with(mappingPokemonListState) {
            val maxItem = sortedPokemonList(mappingPokemonList).first().copy(isSelected = true)
            mappingPokemonList.remove(maxItem)
            mappingPokemonList.add(0, maxItem)
            return@with mappingPokemonList
        }

    fun changeSpecifications(@IdRes checkBoxId: Int, isChecked: Boolean) {
        val specification = when (checkBoxId) {
            R.id.attackCheckBox -> Specifications.ATTACK
            R.id.defenseCheckBox -> Specifications.DEFENSE
            R.id.hpCheckBox -> Specifications.HP
            else -> null
        }
        if (specification != null) {
            val specificationsSet = mappingPokemonListState.specifications
            if (isChecked) specificationsSet.add(specification)
            else specificationsSet.remove(specification)
            mappingPokemonListState =
                mappingPokemonListState.copy(specifications = specificationsSet)
        }
        setSuccessData(mappingPokemonList())
    }

    fun changeTypeMapping(@IdRes radioButtonId: Int) {
        val type = when (radioButtonId) {
            R.id.searchRadioButton -> TypeMapping.SEARCH
            R.id.sortRadioButton -> TypeMapping.SORT
            else -> null
        }
        if (type != null) {
            mappingPokemonListState = mappingPokemonListState.copy(typeMapping = type)
        }
        setSuccessData(items = mappingPokemonList())
    }

    private fun setSuccessData(items: List<ListItem>) {
        val listForAdapterDelegate: MutableList<ListItem> = items.toMutableList().apply {
            // если страницы не закончились, то добавляем вниз лоудер
            if (this.size >= PAGE_SIZE) {
                add(LoaderModel(0))
            }
        }
        val action = when(mappingPokemonListState.typeMapping) {
            TypeMapping.SEARCH -> Action.START_POSITION
            TypeMapping.SORT -> Action.NOTHING
        }
        _viewStateStateFlow.value = ViewState.Success(
            data = listForAdapterDelegate,
            action = action
        )
    }

    data class MappingPokemonListState(
        val specifications: MutableSet<Specifications>,
        val typeMapping: TypeMapping
    ) {
        enum class Specifications {
            ATTACK, DEFENSE, HP
        }

        enum class TypeMapping {
            SEARCH, SORT
        }
    }

    sealed class ViewState {
        class Success(val data: List<ListItem>, val action: Action) : ViewState() {
            enum class Action {
                NOTHING, START_POSITION
            }
        }

        object Loading : ViewState()
        class Error(message: String) : ViewState()
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}