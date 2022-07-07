package com.example.pokemontestproject.features.pokemon.data.repository

import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSource
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.PokemonListItemDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokemonNetworkDataSource: PokemonNetworkDataSource) :
    PokemonRepository {

    override suspend fun getPokemonListApi(
        limit: Int,
        offset: Int
    ): Flow<List<PokemonListItemDomainModel>> = flow {
        val pokemonListResponse =
            pokemonNetworkDataSource.getPokemonList(limit = limit, offset = offset)
        val pokemonList = pokemonListResponse.pokemonList?.map { it.mapToDomain() } ?: emptyList()
        emit(pokemonList)
    }.flowOn(Dispatchers.IO)

    override suspend fun getPokemonDetailApi(name: String): PokemonDetailDomainModel =
        withContext(Dispatchers.IO) {
            return@withContext pokemonNetworkDataSource.getPokemonDetail(name = name).mapToDomain()
        }
}