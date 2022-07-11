package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail

import com.example.pokemontestproject.features.pokemon.data.db.entities.PokemonDetailEntity
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.squareup.moshi.Json

class PokemonDetailResponse(
    @field:Json(name = "base_experience")
    val baseExperience: Int?,
    val height: Int?,
    val id: Int?,
    @field:Json(name = "is_default")
    val isDefault: Boolean?,
    val name: String?,
    val order: Int?,
    val species: Species?,
    val sprites: Sprites?,
    val stats: List<Stat>?,
    val types: List<Type>?,
    val weight: Int?
) {
    fun mapToDomain() = PokemonDetailDomainModel(
        baseExperience = this.baseExperience,
        height = this.height,
        id = this.id,
        isDefault = this.isDefault,
        name = this.name,
        order = this.order,
        species = this.species?.mapToDomain(),
        sprites = this.sprites?.mapToDomain(),
        stats = this.stats?.map { it.mapToDomain() } ?: emptyList(),
        types = this.types?.map { it.typeInfo?.mapToDomain() } ?: emptyList(),
        weight = this.weight
    )

    fun mapToEntity() = PokemonDetailEntity(
        baseExperience = this.baseExperience,
        height = this.height,
        id = this.id,
        isDefault = this.isDefault,
        name = this.name,
        order = this.order,
        species = this.species?.mapToEntity(),
        sprites = this.sprites?.mapToEntity(),
        stats = this.stats?.map { it.mapToEntity() } ?: emptyList(),
        types = this.types?.map { it.typeInfo?.mapToEntity() } ?: emptyList(),
        weight = this.weight
    )
}