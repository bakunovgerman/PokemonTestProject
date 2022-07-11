package com.example.pokemontestproject.features.pokemon.data.db

import com.example.pokemontestproject.features.pokemon.data.db.dao.PokemonDao
import com.example.pokemontestproject.features.pokemon.data.db.entities.PokemonDetailEntity
import javax.inject.Inject

interface PokemonDbDataSource {

    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDetailEntity>
    suspend fun insertPokemonList(list: List<PokemonDetailEntity>)
    suspend fun getPokemonCount(): Int
}

class PokemonDbDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : PokemonDbDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDetailEntity> {
        return pokemonDao.getPokemonList(limit = limit, offset = offset)
    }

    override suspend fun insertPokemonList(list: List<PokemonDetailEntity>) {
        pokemonDao.insertPokemonList(list = list)
    }

    override suspend fun getPokemonCount(): Int = pokemonDao.getPokemonCount()
}