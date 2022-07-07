package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_list

import com.squareup.moshi.Json

data class PokemonListResponse(
    @field:Json(name = "results")
    val pokemonList: List<PokemonListItemApi>?
)