package com.example.pokemontestproject.features.pokemon.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem

class PokemonDiffUtilsCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(
        oldItem: ListItem,
        newItem: ListItem
    ): Boolean = oldItem.idItem == newItem.idItem

    override fun areContentsTheSame(
        oldItem: ListItem,
        newItem: ListItem
    ): Boolean = oldItem.equals(newItem)
}