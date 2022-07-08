package com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces

import androidx.paging.PagingData
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import kotlinx.coroutines.flow.Flow

interface GetPagedPokemonUseCase {
    operator fun invoke(limit: Int): Flow<PagingData<PokemonDetailDomainModel>>
}