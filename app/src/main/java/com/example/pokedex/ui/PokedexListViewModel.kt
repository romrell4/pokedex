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

class PokedexListViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    init {
        if (savedStateHandle.get<PokedexListState>(STATE_KEY)?.cachedList?.isNotEmpty() != false) {
            loadPokemonList()
        }
    }

    val viewStateFlow: Flow<PokedexListViewState> by lazy {
        stateFlow
            .onEach { savedStateHandle[STATE_KEY] = it }
            .map { it.toViewState() }
            .distinctUntilChanged()
    }

    private val stateFlow: MutableStateFlow<PokedexListState> = MutableStateFlow(
        savedStateHandle.get(STATE_KEY) ?: PokedexListState(
            listState = Loading
        )
    )

    fun loadPokemonList() {
        viewModelScope.launch {
            val getAllPokemonUseCase = DI.instance.getAllPokemonUseCase(viewModelScope)
            getAllPokemonUseCase.execute().collect {
                stateFlow.value = stateFlow.value.copy(
                    listState = it, cachedList = when (it) {
                        is Error, Loading -> stateFlow.value.cachedList
                        is Success -> it.successData.results
                    }
                )
            }
        }
    }
}

@Parcelize
data class PokedexListState(
    val listState: AsyncOperation<PokemonList>,
    val cachedList: List<ListItem> = emptyList()
) : Parcelable {
    fun toViewState() =
        when (listState) {
            is Error -> PokedexListViewState(cachedList.map { it.toPokedexListItem() }, listState.error, false)
            Loading -> PokedexListViewState(cachedList.map { it.toPokedexListItem() }, null, true)
            is Success -> PokedexListViewState(
                list = listState.successData.results.map { it.toPokedexListItem() },
                error = null,
                isLoading = false
            )
        }

    private fun ListItem.toPokedexListItem() = PokedexListViewState.PokedexListItem(
        name = name.replaceFirstChar { it.uppercase() },
        id = url.split("/").lastOrNull { it.isNotEmpty() } ?: ""
    )
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
