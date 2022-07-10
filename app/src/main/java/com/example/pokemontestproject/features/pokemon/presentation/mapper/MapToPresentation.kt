package com.example.pokemontestproject.features.pokemon.presentation.mapper

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonPresentationModel

fun PokemonDetailDomainModel.mapToPresentation() = PokemonPresentationModel(
    baseExperience = this.baseExperience,
    height = this.height,
    id = this.id,
    isDefault = this.isDefault,
    name = this.name,
    order = this.order,
    species = this.species,
    sprites = this.sprites,
    stats = this.stats,
    types = this.types,
    weight = this.weight
)

private const val ATTACK = "attack"
private const val DEFENSE = "defense"
private const val HP = "hp"
fun PokemonPresentationModel.mapToListItemModel() = PokemonListItemPresentationModel(
    id = id,
    name = name ?: "",
    imageUrl = sprites?.frontDefault,
    attack = stats.find { it.name == ATTACK }?.baseStat ?: 0,
    defense = stats.find { it.name == DEFENSE }?.baseStat ?: 0,
    hp = stats.find { it.name == HP }?.baseStat ?: 0
)