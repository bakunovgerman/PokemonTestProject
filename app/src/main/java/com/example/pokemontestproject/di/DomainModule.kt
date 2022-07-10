package com.example.pokemontestproject.di

import com.example.pokemontestproject.features.pokemon.domain.usecases.GetPokemonListUseCaseImpl
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindGetPokemonListUseCase(impl: GetPokemonListUseCaseImpl): GetPokemonListUseCase
}