package com.example.pokedex.domain

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ListItem>
)