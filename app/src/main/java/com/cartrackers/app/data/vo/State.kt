package com.cartrackers.app.data.vo

sealed class State<out T> {
    object Loading : State<Nothing>()

    object Empty: State<Nothing>()

    data class Data<T>(val data: T) : State<T>()

    data class Error<T>(val error: Throwable) : State<T>()
}