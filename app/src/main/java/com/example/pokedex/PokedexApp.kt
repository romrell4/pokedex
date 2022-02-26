package com.example.pokedex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.pokedex.repo.PokedexRepository
import com.example.pokedex.repo.PokedexRepositoryImpl
import com.example.pokedex.usecase.GetAllPokemonUseCase
import com.example.pokedex.usecase.GetAllPokemonUseCaseImpl

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()

        DI.instance = DI(applicationContext)
    }
}

class DI(
    private val context: Context
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: DI
            internal set
    }
    // Repos
    private val pokedexRepo: PokedexRepository by lazy { PokedexRepositoryImpl(context) }

    // UseCase
    val getAllPokemonUseCase: GetAllPokemonUseCase by lazy { GetAllPokemonUseCaseImpl(pokedexRepo) }
}