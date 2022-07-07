package com.example.pokemontestproject.features.pokemon.domain.repository

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.PokemonListItemDomainModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonListApi(limit: Int, offset: Int): Flow<List<PokemonListItemDomainModel>>

    suspend fun getPokemonDetailApi(name: String): PokemonDetailDomainModel
}