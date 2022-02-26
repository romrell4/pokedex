package com.example.pokedex.usecase

import com.example.pokedex.domain.PokemonList
import com.example.pokedex.repo.PokedexRepository
import retrofit2.await

interface GetAllPokemonUseCase {
    suspend fun execute(): PokemonList
}

class GetAllPokemonUseCaseImpl(
    private val repo: PokedexRepository
) : GetAllPokemonUseCase {
    override suspend fun execute() = repo.getAllPokemon().await()
}