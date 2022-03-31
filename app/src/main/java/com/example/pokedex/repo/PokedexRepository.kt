package com.example.pokedex.repo

import com.example.pokedex.domain.PokemonList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
        fun getAllPokemon(
//            @Query("offset") offset: String,
//            @Query("limit") limit: String
        ): Call<PokemonList>

//        @GET("pokemon/{id}")
//        fun getPokemonDetail(@ParameterName("id") id: String): Call<PokemonList>
    }

    override suspend fun getAllPokemon() = client.getAllPokemon()
}