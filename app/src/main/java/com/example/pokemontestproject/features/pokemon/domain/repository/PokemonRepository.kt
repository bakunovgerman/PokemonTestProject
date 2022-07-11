package com.example.pokemontestproject.features.pokemon.domain.repository

import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail.PokemonDetailResponse
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailWithPagingDomainData
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_list.PokemonListWithPagingDataDomainModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonNamesListApi(
        limit: Int,
        offset: Int
    ): PokemonListWithPagingDataDomainModel

    suspend fun getPokemonDetailApi(name: String): PokemonDetailResponse

    suspend fun getPokemonDetailListWithPagingData(
        limit: Int,
        offset: Int
    ): Flow<PokemonDetailWithPagingDomainData>
}