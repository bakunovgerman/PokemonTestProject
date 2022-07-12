package com.example.pokemontestproject.features.pokemon.data.repository

import android.content.Context
import com.example.pokemontestproject.core.utils.isOnline
import com.example.pokemontestproject.features.pokemon.data.db.PokemonDbDataSource
import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSource
import com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail.PokemonDetailResponse
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailWithPagingDomainData
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_list.PokemonListWithPagingDataDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val context: Context,
    private val pokemonNetworkDataSource: PokemonNetworkDataSource,
    private val pokemonDbDataSource: PokemonDbDataSource
) : PokemonRepository {

    override suspend fun getPokemonNamesListApi(
        limit: Int,
        offset: Int
    ): PokemonListWithPagingDataDomainModel = withContext(Dispatchers.IO) {
        val response = pokemonNetworkDataSource.getPokemonList(limit = limit, offset = offset)
        return@withContext PokemonListWithPagingDataDomainModel(
            totalCountItems = response.count ?: 0,
            pokemonNamesList = response.pokemonList?.map { it.mapToDomain() } ?: emptyList()
        )
    }

    override suspend fun getPokemonDetailApi(name: String): PokemonDetailResponse =
        withContext(Dispatchers.IO) {
            return@withContext pokemonNetworkDataSource.getPokemonDetail(name = name)
        }

    override suspend fun getPokemonDetailListWithPagingData(
        limit: Int,
        offset: Int
    ): Flow<PokemonDetailWithPagingDomainData> = flow {
        val data = if (isOnline(context)) {
            val pokemonNamesListWithPaging = getPokemonNamesListApi(limit = limit, offset = offset)
            val pokemonDetailListApi = pokemonNamesListWithPaging.pokemonNamesList.map { pokemon ->
                getPokemonDetailApi(
                    name = pokemon.name
                )
            }
            pokemonDbDataSource.insertPokemonList(pokemonDetailListApi.map { it.mapToEntity() })
            PokemonDetailWithPagingDomainData(
                totalCountItems = pokemonNamesListWithPaging.totalCountItems,
                pokemonDetailList = pokemonDetailListApi.map { it.mapToDomain() }
            )
        } else {
            val totalCountPokemon = pokemonDbDataSource.getPokemonCount()
            val pokemonDetailList = pokemonDbDataSource.getPokemonList(
                limit = limit,
                offset = offset
            ).map { it.mapToDomain() }
            PokemonDetailWithPagingDomainData(
                totalCountItems = totalCountPokemon,
                pokemonDetailList = pokemonDetailList
            )
        }
        emit(data)
    }.flowOn(Dispatchers.IO)
}