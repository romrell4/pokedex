package com.example.pokedex.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class AsyncOperation<out T> : Parcelable

@Parcelize
object Loading : AsyncOperation<Nothing>()

@Parcelize
data class Success<T: Parcelable>(val successData: T) : AsyncOperation<T>()

@Parcelize
data class Error(val error: Throwable) : AsyncOperation<Nothing>()