package com.example.pokedex.domain

data class Pokemon(
    val abilities: List<Ability>,
    val height: Int,
    val id: Int,
    val name: String,
    val sprite: Sprite,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
) {
    data class Sprite(
        val frontDefault: String
    )

    data class Stat(
        val baseStat: Int,
        val effort: Int,
        val stat: ListItem
    )

    data class Ability(
        val ability: ListItem,
        val isHidden: Boolean,
        val slot: Int
    )

    data class Type(
        val slot: Int,
        val type: ListItem
    )
}