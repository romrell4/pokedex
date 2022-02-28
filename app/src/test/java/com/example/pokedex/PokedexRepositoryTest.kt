package com.example.pokedex

import com.example.pokedex.repo.PokedexRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PokedexRepositoryTest {
    @Test
    fun `when trying to load pokemon, the client is called properly`() = runBlocking {
        val mockClient = mockk<PokedexRepositoryImpl.PokedexClient>(relaxed = true)
        PokedexRepositoryImpl(mockClient).getAllPokemon()

        verify(exactly = 1) {
            mockClient.getAllPokemon()
        }
    }
}
