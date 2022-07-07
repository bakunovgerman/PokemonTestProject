package com.example.pokemontestproject.features.pokemon.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel

class PokemonDiffUtilsCallback : DiffUtil.ItemCallback<PokemonListItemPresentationModel>() {
    override fun areItemsTheSame(
        oldItem: PokemonListItemPresentationModel,
        newItem: PokemonListItemPresentationModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PokemonListItemPresentationModel,
        newItem: PokemonListItemPresentationModel
    ): Boolean = oldItem == newItem
}