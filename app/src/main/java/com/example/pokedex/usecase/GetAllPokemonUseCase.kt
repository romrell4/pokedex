package com.example.pokedex.usecase

import com.example.pokedex.domain.PokemonList
import com.example.pokedex.repo.PokedexRepository
import com.example.pokedex.domain.AsyncOperation
import com.example.pokedex.domain.Error
import com.example.pokedex.domain.Loading
import com.example.pokedex.domain.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

interface GetAllPokemonUseCase {
    suspend fun execute(): Flow<AsyncOperation<PokemonList>>
}

class GetAllPokemonUseCaseImpl(
    private val repo: PokedexRepository,
    private val scope: CoroutineScope
) : GetAllPokemonUseCase {
    override suspend fun execute(): Flow<AsyncOperation<PokemonList>> {
        val flow = MutableStateFlow<AsyncOperation<PokemonList>>(Loading)
        scope.launch {
            try {
                flow.value = Success(repo.getAllPokemon().await())
            } catch (e: Throwable) {
                flow.value = Error(e)
            }
        }
        return flow
    }
}
