package com.example.pokemontestproject.features.pokemon.domain.usecases

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import javax.inject.Inject

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : GetPokemonListUseCase {

    override suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): List<PokemonDetailDomainModel> {
        val pokemonList = pokemonRepository.getPokemonListApi(limit = limit, offset = offset)
        return pokemonList.map { pokemon -> pokemonRepository.getPokemonDetailApi(name = pokemon.name) }
    }
}