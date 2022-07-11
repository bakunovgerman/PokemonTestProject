package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_list

import com.squareup.moshi.Json

data class PokemonListResponse(
    val count: Int?,
    @field:Json(name = "results")
    val pokemonList: List<PokemonListItemApi>?
)