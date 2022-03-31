package com.example.pokedex.ui

import android.os.Parcelable
import androidx.lifecycle.*
import com.example.pokedex.DI
import com.example.pokedex.domain.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

private const val STATE_KEY = "POKEDEX_LIST_STATE_KEY"

class PokedexListViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
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

    init {
        if (savedStateHandle.get<PokedexListState>(STATE_KEY)?.cachedList?.isNotEmpty() != false) {
            loadPokemonList()
        }
    }

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

    fun expandCard() {

    }
}

@Parcelize
data class PokedexListState(
    val listState: AsyncOperation<PokemonList>,
    val cachedList: List<ListItem> = emptyList()
) : Parcelable {
    fun toViewState() =
        when (listState) {
            is Error -> PokedexListViewState(
                cachedList.map { it.toPokedexListItem() },
                listState.error,
                false
            )
            Loading -> PokedexListViewState(cachedList.map { it.toPokedexListItem() }, null, true)
            is Success -> PokedexListViewState(
                list = listState.successData.results.map { it.toPokedexListItem() },
                error = null,
                showLoadingSpinner = false
            )
        }

    private fun ListItem.toPokedexListItem(): PokedexListViewState.PokedexListItem {
        val id = url.split("/").lastOrNull { it.isNotEmpty() } ?: ""

        return PokedexListViewState.PokedexListItem(
            name = name.replaceFirstChar { it.uppercase() },
            id = when (id.length) {
                1 -> "#00$id"
                2 -> "#0$id"
                else -> "#$id"
            },
            image_url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
            expanded = false
        )
    }
}

data class PokedexListViewState(
    val list: List<PokedexListItem>,
    val error: Throwable? = null,
    val showLoadingSpinner: Boolean = false,
) {
    data class PokedexListItem(
        val name: String,
        val id: String,
        val image_url: String,
        val expanded: Boolean
    )
}
