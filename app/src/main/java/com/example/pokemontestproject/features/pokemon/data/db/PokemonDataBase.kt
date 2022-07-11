package com.example.pokemontestproject.features.pokemon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemontestproject.features.pokemon.data.db.dao.PokemonDao
import com.example.pokemontestproject.features.pokemon.data.db.entities.PokemonDetailEntity

@Database(
    entities = [PokemonDetailEntity::class],
    version = 1
)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}