package com.example.pokedex.repo

import com.example.pokedex.domain.PokemonList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PokedexRepository {
    suspend fun getAllPokemon(): Call<PokemonList>
}

class PokedexRepositoryImpl(
    private val client: PokedexClient = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokedexClient::class.java)
) : PokedexRepository {

    interface PokedexClient {
        @GET ("pokemon")
        fun getAllPokemon(): Call<PokemonList>
    }

    override suspend fun getAllPokemon() = client.getAllPokemon()
}
