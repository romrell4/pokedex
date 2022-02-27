package com.example.pokedex.ui

import android.os.Parcelable
import androidx.lifecycle.*
import com.example.pokedex.DI
import com.example.pokedex.domain.ListItem
import com.example.pokedex.domain.PokemonList
import com.example.pokedex.usecase.GetAllPokemonUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

private const val STATE_KEY = "POKEDEX_LIST_STATE_KEY"

class PokedexListViewModel @JvmOverloads constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    init {
        loadPokemonList()
    }

    val viewStateFlow: Flow<PokedexListViewState> by lazy {
        stateFlow.map { it.toViewState() }.distinctUntilChanged()
    }

    private val stateFlow: MutableStateFlow<PokedexListState> = MutableStateFlow(
        savedStateHandle.get(STATE_KEY) ?: PokedexListState(
            pokemonList = null
        )
    )

    private fun loadPokemonList() {
        viewModelScope.launch {
            val getAllPokemonUseCase: GetAllPokemonUseCase = DI.instance.getAllPokemonUseCase(viewModelScope)
            stateFlow.value = stateFlow.value.copy(pokemonList = PokedexListState(getAllPokemonUseCase.execute()))
        }
    }
}

@Parcelize
data class PokedexListState(
    val pokemonList: PokemonList?
) : Parcelable {
    fun toViewState() = PokedexListViewState(
        list = pokemonList?.results
    )
}

data class PokedexListViewState(
    val list: List<ListItem>?
)
