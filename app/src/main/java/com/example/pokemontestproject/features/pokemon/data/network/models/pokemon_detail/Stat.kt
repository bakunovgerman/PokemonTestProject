package com.example.pokemontestproject.features.pokemon.data.network.models.pokemon_detail


import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.StatDomainModel
import com.squareup.moshi.Json

class Stat(
    @field:Json(name = "base_stat")
    val baseStat: Int?,
    val effort: Int?,
    @field:Json(name = "stat")
    val statInfo: StatInfo?
) {
    fun mapToDomain() =
        StatDomainModel(baseStat = baseStat, effort = effort, name = statInfo?.name ?: "")
}