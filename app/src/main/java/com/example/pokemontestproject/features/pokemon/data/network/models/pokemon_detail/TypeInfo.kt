package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.TypeDomainModel

class TypeInfo(
    val name: String?
) {
    fun mapToDomain() = TypeDomainModel(name = name ?: "")
}