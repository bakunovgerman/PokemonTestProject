package com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailWithPagingDomainData
import kotlinx.coroutines.flow.Flow

interface GetPokemonListUseCase {
    suspend operator fun invoke(limit: Int, offset: Int): Flow<PokemonDetailWithPagingDomainData>
}