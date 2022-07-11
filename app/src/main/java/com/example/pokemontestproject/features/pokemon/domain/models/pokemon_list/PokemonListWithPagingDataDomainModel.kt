package com.example.pokemontestproject.features.pokemon.domain.models.pokemon_list

class PokemonListWithPagingDataDomainModel(
    val totalCountItems: Int,
    val pokemonNamesList: List<PokemonNameListItemDomainModel>
)