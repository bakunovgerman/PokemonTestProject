package com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class StatDomainModel(
    val baseStat: Int?,
    val effort: Int?,
    val name: String
) : Parcelable