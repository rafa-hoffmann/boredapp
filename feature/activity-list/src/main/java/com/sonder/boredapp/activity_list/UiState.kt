package com.sonder.boredapp.activity_list

sealed interface UiState<out T> {
    data class Success<T>(val value: T) : UiState<T>
    object Error : UiState<Nothing>
    object Loading : UiState<Nothing>
}
