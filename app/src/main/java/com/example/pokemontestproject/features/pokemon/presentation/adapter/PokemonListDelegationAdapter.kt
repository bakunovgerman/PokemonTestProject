package com.example.pokemontestproject.features.pokemon.presentation.adapter

import com.example.pokemontestproject.features.pokemon.presentation.models.ListItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PokemonListDelegationAdapter(vararg adapterDelegate: AdapterDelegate<List<ListItem>>) :
    AsyncListDifferDelegationAdapter<ListItem>(
        PokemonDiffUtilsCallback(),
        *adapterDelegate
    )