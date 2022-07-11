package com.example.pokemontestproject.features.pokemon.presentation.models

import android.os.Parcelable
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.SpeciesDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.SpritesDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.StatDomainModel
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.TypeDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonPresentationModel(
    val baseExperience: Int?,
    val height: Int?,
    val id: Int?,
    val isDefault: Boolean?,
    val name: String?,
    val order: Int?,
    val species: SpeciesDomainModel?,
    val sprites: SpritesDomainModel?,
    val stats: List<StatDomainModel>,
    val types: List<TypeDomainModel?>,
    val weight: Int?
) : Parcelable