package com.example.pokemontestproject.features.pokemon.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPagedPokemonUseCase
import com.example.pokemontestproject.features.pokemon.domain.usecases.interfaces.GetPokemonListUseCase
import com.example.pokemontestproject.features.pokemon.presentation.paging.PokemonPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedPokemonUseCaseImpl @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : GetPagedPokemonUseCase {

    override fun invoke(limit: Int): Flow<PagingData<PokemonDetailDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                initialLoadSize = limit,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                PokemonPagingSource { limitPage: Int, offset: Int ->
                    getPokemonListUseCase(
                        limit = limitPage,
                        offset = offset
                    )
                }
            }
        ).flow
    }

    companion object {
        private const val PREFETCH_DISTANCE = 1
    }
}