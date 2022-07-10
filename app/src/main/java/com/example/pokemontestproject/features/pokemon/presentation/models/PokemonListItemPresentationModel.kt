package com.example.pokemontestproject.features.pokemon.presentation.models

data class PokemonListItemPresentationModel(
    val id: Int?,
    val name: String,
    val imageUrl: String?,
    val attack: Int?,
    val defense: Int?,
    val hp: Int?,
    val isSelected: Boolean = false
) : ListItem {
    override val idItem: Int = id ?: name.hashCode()
}