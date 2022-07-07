package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.SpeciesDomainModel

class Species(
    val name: String?
) {
    fun mapToDomain() = SpeciesDomainModel(name = name ?: "")
}