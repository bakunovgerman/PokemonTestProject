package com.example.pokemontestproject.features.pokemon.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.PokemonDetailDomainModel

class PokemonPagingSource(
    private val loader: suspend (limit: Int, offset: Int) -> List<PokemonDetailDomainModel>
) : PagingSource<Int, PokemonDetailDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDetailDomainModel> {
        val pageIndex = params.key ?: 0

        return try {
            val pokemonList = loader(params.loadSize, pageIndex)
            LoadResult.Page(
                data = pokemonList,
                prevKey = null,
                nextKey = pageIndex + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonDetailDomainModel>): Int = 0
}