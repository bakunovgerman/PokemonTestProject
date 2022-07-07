package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail

import com.squareup.moshi.Json

class Type(
    val slot: Int?,
    @field:Json(name = "type")
    val typeInfo: TypeInfo?
)