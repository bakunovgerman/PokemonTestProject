package com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail

class PokemonDetailDomainModel(
    val baseExperience: Int?,
    val height: Int?,
    val id: Int?,
    val isDefault: Boolean?,
    val name: String?,
    val order: Int?,
    val species: SpeciesDomainModel?,
    val sprites: SpritesDomainModel?,
    val stats: List<StatDomainModel>,
    val types: List<TypeDomainModel?>,
    val weight: Int?
)
