package com.example.pokemontestproject.features.pokemon_detail.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.pokemontestproject.R
import com.example.pokemontestproject.databinding.FragmentPokemonDetailBinding
import com.example.pokemontestproject.features.pokemon.presentation.models.PokemonPresentationModel

class PokemonDetailFragment : Fragment(R.layout.fragment_pokemon_detail) {

    private val binding: FragmentPokemonDetailBinding by viewBinding()
    private val pokemonDetail by lazy { arguments?.getParcelable(ARG_POKEMON_DETAIL) as? PokemonPresentationModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        toolbar.title = String.format(getString(R.string.detail_pokemon_title), pokemonDetail?.name)
        val glide = Glide.with(requireContext())
        val frontImage = pokemonDetail?.sprites?.frontDefault
        val backImage = pokemonDetail?.sprites?.backDefault
        glide.load(frontImage).into(frontImageImageView)
        glide.load(backImage).into(backImageImageView)
        heightTextView.text =
            String.format(getString(R.string.height_info), pokemonDetail?.height.toString())
        weightTextView.text =
            String.format(getString(R.string.weight_info), pokemonDetail?.weight.toString())
        var statsText = ""
        pokemonDetail?.stats?.forEach {
            statsText += "${it.name.replaceFirstChar { char -> char.uppercaseChar() }}: ${it.baseStat}\n"
        }
        statsTextView.text = statsText
        var typeText = ""
        pokemonDetail?.types?.forEach {
            typeText += it?.name
        }
        typeTextView.text = String.format(getString(R.string.type_info), typeText)
    }

    companion object {
        const val ARG_POKEMON_DETAIL = "arg_pokemon_detail"
    }
}