package com.example.pokemontestproject.features.pokemon.presentation.adapter

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.ItemFooterLoaderBinding
import com.example.pokemontestproject.databinding.ItemPokemonLayoutBinding
import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem
import com.example.pokemontestproject.features.pokemon.presentation.models.LoaderModel
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun pokemonAdapterDelegate(itemClickedListener: (PokemonListItemPresentationModel) -> Unit) =
    adapterDelegateViewBinding<PokemonListItemPresentationModel, ListItem, ItemPokemonLayoutBinding>(
        { layoutInflater, root -> ItemPokemonLayoutBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            with(binding) {
                Glide.with(root).load(item.imageUrl).into(imagePokemon)
                nameTextView.text = item.name
                attackTextView.text =
                    String.format(context.getString(R.string.attack_info), item.attack.toString())
                defenseTextView.text =
                    String.format(context.getString(R.string.defense_info), item.defense.toString())
                hpTextView.text =
                    String.format(context.getString(R.string.hp_info), item.hp.toString())
                idPokemonTextView.text =
                    String.format(context.getString(R.string.id_info), item.id.toString())
                if (item.isSelected) {
                    setColor(R.color.yellow_light, binding.root)
                } else {
                    setColor(R.color.gray_light, binding.root)
                }
            }
        }
    }

fun loaderAdapterDelegate() =
    adapterDelegateViewBinding<LoaderModel, ListItem, ItemFooterLoaderBinding>(
        { layoutInflater, root -> ItemFooterLoaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {}
    }

private fun setColor(@ColorRes colorId: Int, view: View) {
    view.setBackgroundColor(
        ContextCompat.getColor(
            view.context,
            colorId
        )
    )
}