package com.example.pokemontestproject.features.pokemon.data.network

import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail.PokemonDetailResponse
import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_list.PokemonListResponse
import com.example.pokemontestproject.features.pokemon.data.network.service.PokemonService
import retrofit2.Response
import javax.inject.Inject

interface PokemonNetworkDataSource {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse
    suspend fun getPokemonDetail(name: String): PokemonDetailResponse
}

class PokemonNetworkDataSourceImpl @Inject constructor(private val pokemonService: PokemonService) :
    PokemonNetworkDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int) =
        pokemonService.getPokemonList(limit = limit, offset = offset)

    override suspend fun getPokemonDetail(name: String) =
        pokemonService.getPokemonDetail(name = name)
}