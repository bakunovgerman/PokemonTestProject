package com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SpritesDomainModel(
    val backDefault: String?,
    val frontDefault: String?
) : Parcelable