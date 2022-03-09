package com.example.pokedex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ViewHolderPokedexListItemBinding
import com.squareup.picasso.Picasso

class PokedexListAdapter(
    private val viewModel: PokedexListViewModel
) : ListAdapter<PokedexListViewState.PokedexListItem, PokedexListViewHolder>(
    object : DiffUtil.ItemCallback<PokedexListViewState.PokedexListItem>() {
        override fun areItemsTheSame(oldItem: PokedexListViewState.PokedexListItem, newItem: PokedexListViewState.PokedexListItem) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PokedexListViewState.PokedexListItem, newItem: PokedexListViewState.PokedexListItem) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexListViewHolder {
        val binding = ViewHolderPokedexListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokedexListViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: PokedexListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PokedexListViewHolder(
    private val binding: ViewHolderPokedexListItemBinding,
    private val viewModel: PokedexListViewModel
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PokedexListViewState.PokedexListItem) {
        binding.nameText.text = item.name
        binding.idText.text = item.id
        Picasso.get()
            .load(item.image_url)
            .placeholder(R.drawable.ic_default_pokemon)
            .into(binding.pokemonImageHolder)
        binding.card.setOnClickListener {
            viewModel.expandCard()
        }
    }
}