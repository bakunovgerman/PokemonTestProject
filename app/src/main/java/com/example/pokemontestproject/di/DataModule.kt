package com.example.pokemontestproject.di

import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSource
import com.example.pokemontestproject.features.pokemon.data.network.PokemonNetworkDataSourceImpl
import com.example.pokemontestproject.features.pokemon.data.repository.PokemonRepositoryImpl
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    fun bindPokemonNetworkDataSource(impl: PokemonNetworkDataSourceImpl): PokemonNetworkDataSource
}