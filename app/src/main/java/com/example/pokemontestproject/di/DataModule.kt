package com.example.pokemontestproject.di

import android.content.Context
import androidx.room.Room
import com.example.pokemontestproject.features.pokemon.data.db.PokemonDataBase
import com.example.pokemontestproject.features.pokemon.data.db.PokemonDbDataSource
import com.example.pokemontestproject.features.pokemon.data.db.PokemonDbDataSourceImpl
import com.example.pokemontestproject.features.pokemon.data.db.dao.PokemonDao
import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSource
import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSourceImpl
import com.example.pokemontestproject.features.pokemon.data.repository.PokemonRepositoryImpl
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule.DataBindModule::class])
class DataModule {

    @Singleton
    @Provides
    fun providePokemonDataBase(context: Context): PokemonDataBase {
        val dataBaseName = "PokemonDataBase"
        return Room.databaseBuilder(
            context,
            PokemonDataBase::class.java,
            dataBaseName
        ).build()
    }

    @Provides
    fun providePokemonDao(pokemonDataBase: PokemonDataBase): PokemonDao {
        return pokemonDataBase.pokemonDao()
    }

    @Module
    interface DataBindModule {
        @Binds
        fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository

        @Binds
        fun bindPokemonNetworkDataSource(impl: PokemonNetworkDataSourceImpl): PokemonNetworkDataSource

        @Binds
        fun bindPokemonDbDataSource(impl: PokemonDbDataSourceImpl): PokemonDbDataSource
    }
}