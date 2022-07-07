package com.example.pokemontestproject.features.pokemon.domain.usecases

import com.example.pokemontestproject.features.pokemon.domain.models.PokemonListItemDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPokemonListUseCase {

    suspend operator fun invoke(limit: Int, offset: Int): Flow<List<PokemonListItemDomainModel>>
}

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : GetPokemonListUseCase {

    override suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Flow<List<PokemonListItemDomainModel>> {
        return pokemonRepository.getPokemonListApi(limit = limit, offset = offset)
    }
}