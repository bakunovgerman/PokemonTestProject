package com.example.pokemontestproject.features.pokemon.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemontestproject.features.pokemon.data.db.entities.PokemonDetailEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(list: List<PokemonDetailEntity>)

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDetailEntity>

    @Query("SELECT COUNT(id) FROM pokemon")
    suspend fun getPokemonCount(): Int
}