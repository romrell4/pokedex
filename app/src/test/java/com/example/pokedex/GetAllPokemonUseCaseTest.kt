package com.example.pokedex

import com.example.pokedex.domain.AsyncOperation
import com.example.pokedex.domain.Loading
import com.example.pokedex.domain.PokemonList
import com.example.pokedex.domain.Success
import com.example.pokedex.repo.PokedexRepository
import com.example.pokedex.usecase.GetAllPokemonUseCaseImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.await

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@Ignore
class GetAllPokemonUseCaseTest {
    private val mockRepo = mockk<PokedexRepository>(relaxed = true)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `when we execute, we first see a loading state`() = runBlocking {

    }

    @Test
    fun `when the repo succeeds, we get a success state`() = runTest {
        val mockPokemonList = mockk<PokemonList>()
        coEvery { mockRepo.getAllPokemon().await() } returns mockPokemonList
        val flowValues = mutableListOf<AsyncOperation<PokemonList>>()
        val job = launch(Dispatchers.Main) {
            GetAllPokemonUseCaseImpl(mockRepo, this@runTest).execute().collect {
                flowValues.add(it)
            }
        }
        job.join()

        flowValues.last().run {
            assertEquals(this, Success(mockPokemonList))
        }
    }

    @Test
    fun `when the repo fails, we get a error state`() {

    }
}
