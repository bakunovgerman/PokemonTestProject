package com.example.pokemontestproject.features.pokemon.domain.usecases

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : GetPokemonListUseCase {

    override suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Flow<List<PokemonDetailDomainModel>> = flow {
        val pokemonList = pokemonRepository.getPokemonListApi(limit = limit, offset = offset)
        val pokemonDetailList =
            pokemonList.map { pokemon -> pokemonRepository.getPokemonDetailApi(name = pokemon.name) }
        emit(pokemonDetailList)
    }
}