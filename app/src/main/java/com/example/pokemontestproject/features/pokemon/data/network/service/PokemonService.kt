package com.example.pokemontestproject.features.pokemon.data.network.service

import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail.PokemonDetailResponse
import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_list.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailResponse
}