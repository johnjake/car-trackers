package com.cartrackers.app.data.vo

sealed class StateModel<T> {
    data class SuccessItem<T>(val data: T) : StateModel<T>()
    data class SeparatorItem<T>(val description: String) : StateModel<T>()
}
