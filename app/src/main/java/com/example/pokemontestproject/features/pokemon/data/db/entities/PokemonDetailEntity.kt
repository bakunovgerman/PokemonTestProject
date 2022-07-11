package com.example.pokemontestproject.features.pokemon.data.db.entities

import androidx.room.*
import com.example.pokemontestproject.features.pokemon.domain.models.pokemon_detail.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "pokemon")
@TypeConverters(PokemonDetailEntity.PokemonDetailEntityConverter::class)
class PokemonDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "base_experience")
    val baseExperience: Int?,
    @ColumnInfo(name = "height")
    val height: Int?,
    @ColumnInfo(name = "is_default")
    val isDefault: Boolean?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "order")
    val order: Int?,
    @ColumnInfo(name = "species")
    val species: SpeciesDbModel?,
    @ColumnInfo(name = "sprites")
    val sprites: SpritesDbModel?,
    @ColumnInfo(name = "stats")
    val stats: List<StatDbModel>,
    @ColumnInfo(name = "types")
    val types: List<TypeDbModel?>,
    @ColumnInfo(name = "weight")
    val weight: Int?
) {

    class PokemonDetailEntityConverter {

        private val gson = Gson()

        @TypeConverter
        fun fromSpecies(speciesDbModel: SpeciesDbModel): String {
            return speciesDbModel.name
        }

        @TypeConverter
        fun toSpecies(name: String): SpeciesDbModel = SpeciesDbModel(name = name)

        @TypeConverter
        fun fromSprites(spritesDbModel: SpritesDbModel): String {
            return gson.toJson(spritesDbModel)
        }

        @TypeConverter
        fun toSprites(json: String): SpritesDbModel {
            val listType = object : TypeToken<SpritesDbModel>() {}.type
            return gson.fromJson(json, listType)
        }

        @TypeConverter
        fun fromStats(stats: List<StatDbModel>): String {
            return gson.toJson(stats)
        }

        @TypeConverter
        fun toStats(json: String): List<StatDbModel> {
            val listType = object : TypeToken<ArrayList<StatDbModel>>() {}.type
            return gson.fromJson(json, listType)
        }

        @TypeConverter
        fun fromTypes(types: List<TypeDbModel>): String {
            return gson.toJson(types)
        }

        @TypeConverter
        fun toTypes(json: String): List<TypeDbModel> {
            val listType = object : TypeToken<ArrayList<TypeDbModel>>() {}.type
            return gson.fromJson(json, listType)
        }
    }

    class SpeciesDbModel(val name: String) {
        fun mapToDomain() = SpeciesDomainModel(name = name)
    }

    class SpritesDbModel(
        val backDefault: String?,
        val frontDefault: String?
    ) {
        fun mapToDomain() = SpritesDomainModel(
            backDefault = backDefault,
            frontDefault = frontDefault
        )
    }

    class StatDbModel(
        val baseStat: Int?,
        val effort: Int?,
        val name: String
    ) {
        fun mapToDomain() = StatDomainModel(
            baseStat = baseStat,
            effort = effort,
            name = name
        )
    }

    class TypeDbModel(val name: String) {
        fun mapToDomain() = TypeDomainModel(name = name)
    }

    fun mapToDomain() = PokemonDetailDomainModel(
        baseExperience = this.baseExperience,
        height = this.height,
        id = this.id,
        isDefault = this.isDefault,
        name = this.name,
        order = this.order,
        species = this.species?.mapToDomain(),
        sprites = this.sprites?.mapToDomain(),
        stats = this.stats.map { it.mapToDomain() },
        types = this.types.map { it?.mapToDomain() },
        weight = this.weight
    )
}