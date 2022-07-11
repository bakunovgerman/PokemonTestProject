package com.example.pokemontestproject.di

import android.content.Context
import com.example.pokemontestproject.di.view_model.ViewModelModule
import com.example.pokemontestproject.features.pokemon.presentation.PokemonFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, NetworkModule::class, DomainModule::class, ViewModelModule::class],
)
interface AppComponent {

    fun inject(pokemonFragment: PokemonFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}