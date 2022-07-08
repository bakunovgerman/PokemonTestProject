package com.example.pokemontestproject.features.pokemon.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.ItemPokemonLayoutBinding
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonListItemPresentationModel

class PokemonPagingAdapter :
    PagingDataAdapter<PokemonListItemPresentationModel, PokemonPagingAdapter.PokemonViewHolder>(
        PokemonDiffUtilsCallback()
    ) {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPokemonLayoutBinding.bind(view)
        private val context = binding.root.context

        fun bind(item: PokemonListItemPresentationModel) = with(binding) {
            Glide.with(root).load(item.imageUrl).into(imagePokemon)
            nameTextView.text = item.name
            attackTextView.text =
                String.format(context.getString(R.string.attack_info), item.attack.toString())
            defenseTextView.text =
                String.format(context.getString(R.string.defense_info), item.defense.toString())
            hpTextView.text = String.format(context.getString(R.string.hp_info), item.hp.toString())
            if (item.isSelected) {
                setColor(R.color.green)
            } else {
                setColor(R.color.white)
            }
        }

        private fun setColor(@ColorRes colorId: Int) {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(inflater.inflate(R.layout.item_pokemon_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }
}