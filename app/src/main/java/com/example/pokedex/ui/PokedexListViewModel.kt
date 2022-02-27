package com.example.pokedex.ui

import android.os.Parcelable
import androidx.lifecycle.*
import com.example.pokedex.DI
import com.example.pokedex.domain.*
import com.example.pokedex.usecase.GetAllPokemonUseCase
import kotlinx.coroutines.flow.*
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
            listState = Loading
        )
    )

    private fun loadPokemonList() {
        viewModelScope.launch {
            val getAllPokemonUseCase: GetAllPokemonUseCase = DI.instance.getAllPokemonUseCase(viewModelScope)
            getAllPokemonUseCase.execute().collect {
                stateFlow.value = stateFlow.value.copy(listState = it)
            }
        }
    }
}

@Parcelize
data class PokedexListState(
    val listState: AsyncOperation<PokemonList>
) : Parcelable {
    fun toViewState() =
        when (listState) {
            is Error -> PokedexListViewState(emptyList(), listState.error, false)
            Loading -> PokedexListViewState(emptyList(), null, true)
            is Success -> PokedexListViewState(
                list = listState.successData.results.map {
                    PokedexListViewState.PokedexListItem(
                        name = it.name,
                        id = it.url.split("/").lastOrNull() ?: ""
                    )
                },
                error = null,
                isLoading = false
            )
        }
}

data class PokedexListViewState(
    val list: List<PokedexListItem>,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) {
    data class PokedexListItem(
        val name: String,
        val id: String
    )
}
