package com.example.pokedex.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val abilities: List<Ability>,
    val height: Int,
    val id: Int,
    val name: String,
    val sprite: Sprite,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
) : Parcelable {
    @Parcelize
    data class Sprite(
        val frontDefault: String
    ) : Parcelable

    @Parcelize
    data class Stat(
        val baseStat: Int,
        val effort: Int,
        val stat: ListItem
    ) : Parcelable

    @Parcelize
    data class Ability(
        val ability: ListItem,
        val isHidden: Boolean,
        val slot: Int
    ) : Parcelable

    @Parcelize
    data class Type(
        val slot: Int,
        val type: ListItem
    ) : Parcelable
}