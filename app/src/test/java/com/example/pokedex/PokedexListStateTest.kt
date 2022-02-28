package com.example.pokedex

import com.example.pokedex.domain.Loading
import com.example.pokedex.repo.PokedexRepositoryImpl
import com.example.pokedex.ui.PokedexListState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PokedexListStateTest {
    @Test
    fun `when in a loading state, the loading spinner is displayed`() {
        PokedexListState(Loading).toViewState().run {
            assertTrue(showLoadingSpinner)
        }
    }

    @Test
    fun `when in a success state, the results are displayed in the list`() {

    }

    @Test
    fun `when converting a list item, the name should be capitalized`() {

    }

    @Test
    fun `when converting a list item, the id should be extracted from the URL`() {
        // Normal case

        // Trailing slash case

        // Bad URL case
    }
}
