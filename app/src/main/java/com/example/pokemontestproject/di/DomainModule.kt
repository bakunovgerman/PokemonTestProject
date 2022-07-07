package com.example.pokemontestproject.di

import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonDetailUseCaseImpl
import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonListUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindGetPokemonListUseCase(impl: GetPokemonListUseCaseImpl): GetPokemonListUseCase

    @Binds
    fun bindGetPokemonDetailUseCase(impl: GetPokemonDetailUseCaseImpl): GetPokemonDetailUseCase
}