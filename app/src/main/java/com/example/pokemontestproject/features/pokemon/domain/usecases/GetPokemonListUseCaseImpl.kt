package com.example.pokemontestproject.features.pokemon.domain.usecases

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailWithPagingDomainData
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : GetPokemonListUseCase {

    override suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Flow<PokemonDetailWithPagingDomainData> =
        pokemonRepository.getPokemonDetailListWithPagingData(limit = limit, offset = offset)
}