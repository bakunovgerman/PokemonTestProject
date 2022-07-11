package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_list

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_list.PokemonNameListItemDomainModel
import com.squareup.moshi.Json

data class PokemonListItemApi(
    @field:Json(name = "name")
    val name: String?
) {
    fun mapToDomain() = PokemonNameListItemDomainModel(name = this.name ?: "")
}