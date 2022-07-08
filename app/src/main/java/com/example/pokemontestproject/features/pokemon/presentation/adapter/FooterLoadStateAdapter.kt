package com.example.pokemontestproject.features.pokemon.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontestproject.R

class FooterLoadStateAdapter : LoadStateAdapter<FooterLoadStateAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {}

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return LoaderViewHolder(inflate.inflate(R.layout.item_footer_loader, parent, false))
    }
}