package com.example.pokemontestproject.features.pokemon.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontestproject.R
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.MappingState.Specifications
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.MappingState.TypeMapping
import com.example.pokemontestproject.features.pokemon.presentation.PokemonViewModel.ViewState.Success.Action
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToListItemModel
import com.example.pokemontestproject.features.pokemon.presentation.mapper.mapToPresentation
import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem
import com.example.pokemontestproject.features.pokemon.presentation.models.LoaderModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonPresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _viewStateStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewStateStateFlow = _viewStateStateFlow.asStateFlow()
    private var mappingState = MappingState(
        specifications = mutableSetOf(),
        typeMapping = TypeMapping.SEARCH
    )
    private var page = 0
    private val pokemonList = mutableListOf<PokemonPresentationModel>()
    private var totalCountPokemon = 0
    private var lastCountLoadedPokemon = 0

    init {
        getPokemonList(page)
    }

    private fun getPokemonList(offset: Int, isResetList: Boolean = false) {
        viewModelScope.launch {
            getPokemonListUseCase(limit = PAGE_SIZE, offset = offset)
                .catch { t ->
                    _viewStateStateFlow.value = ViewState.Error(message = t.message.toString())
                }
                .collect { pokemonDetailListWithPagingData ->
                    totalCountPokemon = pokemonDetailListWithPagingData.totalCountItems
                    lastCountLoadedPokemon = pokemonDetailListWithPagingData.pokemonDetailList.size
                    if (isResetList) pokemonList.clear()
                    pokemonList.addAll(pokemonDetailListWithPagingData.pokemonDetailList.map {
                        it.mapToPresentation()
                    })

                    setSuccessData(items = mappingPokemonList(), isNeedStartPosition = isResetList)
                }
        }
    }

    fun loadNextPage() = getPokemonList(offset = ++page * PAGE_SIZE)

    fun loadCurrentPage() = getPokemonList(offset = page * PAGE_SIZE)

    fun loadRandomPage() {
        page = Random.nextInt(0, totalCountPokemon / PAGE_SIZE)
        getPokemonList(offset = page * PAGE_SIZE, isResetList = true)
    }

    private fun mappingPokemonList(): List<PokemonListItemPresentationModel> =
        with(mappingState) {
            val mappedPokemonList = pokemonList.map { it.mapToListItemModel() }.toMutableList()
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
    ): List<PokemonListItemPresentationModel> = with(mappingState) {
        when {
            specifications.contains(Specifications.ATTACK)
                    && specifications.contains(Specifications.DEFENSE)
                    && specifications.contains(Specifications.HP) -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.defense },
                        { it.hp })
                ).reversed()
            }
            specifications.contains(Specifications.ATTACK) && specifications.contains(Specifications.DEFENSE) -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.defense })
                ).reversed()
            }
            specifications.contains(Specifications.ATTACK) && specifications.contains(Specifications.HP) -> {
                return mappedPokemonList.sortedWith(
                    compareBy(
                        { it.attack },
                        { it.hp })
                ).reversed()
            }
            specifications.contains(Specifications.DEFENSE) && specifications.contains(
                Specifications.HP
            ) -> {
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
        with(mappingState) {
            val maxItem = sortedPokemonList(mappingPokemonList).first().copy(isSelected = true)
            val pokemonListNotSelected =
                mappingPokemonList.map { it.copy(isSelected = false) }.toMutableList()
            val removeIndex = pokemonListNotSelected.indexOfItemById(maxItem.id)
            if (removeIndex != -1) pokemonListNotSelected.removeAt(removeIndex)
            pokemonListNotSelected.add(0, maxItem)
            return@with pokemonListNotSelected
        }

    private fun List<PokemonListItemPresentationModel>.indexOfItemById(id: Int?): Int {
        val item = this.find { it.id == id }
        return this.indexOf(item)
    }

    fun changeSpecifications(@IdRes checkBoxId: Int, isChecked: Boolean) {
        val specification = when (checkBoxId) {
            R.id.attackCheckBox -> Specifications.ATTACK
            R.id.defenseCheckBox -> Specifications.DEFENSE
            R.id.hpCheckBox -> Specifications.HP
            else -> null
        }
        if (specification != null) {
            val specificationsSet = mappingState.specifications
            if (isChecked) specificationsSet.add(specification)
            else specificationsSet.remove(specification)
            mappingState =
                mappingState.copy(specifications = specificationsSet)
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
            mappingState = mappingState.copy(typeMapping = type)
        }
        setSuccessData(items = mappingPokemonList())
    }

    private fun setSuccessData(items: List<ListItem>, isNeedStartPosition: Boolean = false) {
        val listForAdapterDelegate: MutableList<ListItem> = items.toMutableList().apply {
            // if the pages are not finished, then add the lauder down
            if (this.size < totalCountPokemon && lastCountLoadedPokemon == PAGE_SIZE) {
                add(LoaderModel(LOADER_ID))
            }
        }
        val action = if (mappingState.typeMapping == TypeMapping.SEARCH
            && mappingState.specifications.isNotEmpty() || isNeedStartPosition
        ) {
            Action.START_POSITION
        } else {
            Action.NOTHING
        }
        _viewStateStateFlow.value = ViewState.Success(
            data = listForAdapterDelegate,
            action = action
        )
    }

    fun getPokemonById(idPokemon: Int): PokemonPresentationModel? {
        return pokemonList.find { it.id == idPokemon }
    }

    data class MappingState(
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
        class Error(val message: String) : ViewState()
    }

    companion object {
        const val PAGE_SIZE = 30
        private const val LOADER_ID = 0
    }
}