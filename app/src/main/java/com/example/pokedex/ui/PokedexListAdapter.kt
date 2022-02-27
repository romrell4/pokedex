package com.example.pokedex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ViewHolderPokedexListItemBinding

class PokedexListAdapter : ListAdapter<PokedexListViewState.PokedexListItem, PokedexListViewHolder>(
    object : DiffUtil.ItemCallback<PokedexListViewState.PokedexListItem>() {
        override fun areItemsTheSame(oldItem: PokedexListViewState.PokedexListItem, newItem: PokedexListViewState.PokedexListItem) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PokedexListViewState.PokedexListItem, newItem: PokedexListViewState.PokedexListItem): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexListViewHolder {
        val binding = ViewHolderPokedexListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokedexListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PokedexListViewHolder(private val binding: ViewHolderPokedexListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PokedexListViewState.PokedexListItem) {
        binding.nameText.text = item.name
        binding.idText.text = item.id
    }
}