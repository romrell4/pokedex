package com.example.pokedex.usecase

sealed class AsyncOperation<out T>
object Loading : AsyncOperation<Nothing>()
data class Success<T>(val successData: T) : AsyncOperation<T>()
data class Error(val error: Throwable) : AsyncOperation<Nothing>()