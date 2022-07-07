package com.example.pokemontestproject.features.pokemon.domain.usecases

import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

interface GetPokemonDetailUseCase {
    suspend operator fun invoke(name: String): PokemonDetailDomainModel
}

class GetPokemonDetailUseCaseImpl @Inject constructor(private val pokemonRepository: PokemonRepository) :
    GetPokemonDetailUseCase {

    override suspend fun invoke(name: String): PokemonDetailDomainModel {
        return pokemonRepository.getPokemonDetailApi(name = name)
    }
}