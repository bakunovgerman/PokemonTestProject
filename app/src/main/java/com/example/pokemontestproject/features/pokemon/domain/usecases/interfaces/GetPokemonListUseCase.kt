package com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel

interface GetPokemonListUseCase {
    suspend operator fun invoke(limit: Int, offset: Int): List<PokemonDetailDomainModel>
}