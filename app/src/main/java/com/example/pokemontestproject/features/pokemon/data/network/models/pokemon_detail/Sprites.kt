package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail


import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.SpritesDomainModel
import com.squareup.moshi.Json

class Sprites(
    @field:Json(name = "back_default")
    val backDefault: String?,
    @field:Json(name = "front_default")
    val frontDefault: String?
) {
    fun mapToDomain() = SpritesDomainModel(backDefault = backDefault, frontDefault = frontDefault)
}