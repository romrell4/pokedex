package com.example.pokedex.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ViewHolderPokedexListItemBinding
import com.example.pokedex.domain.ListItem

class PokedexListAdapter : ListAdapter<ListItem, PokedexListViewHolder>(
    object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
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
    fun bind(item: ListItem) {
        binding.nameText.text = item.name
        binding.numberText.text = item.url
    }
}