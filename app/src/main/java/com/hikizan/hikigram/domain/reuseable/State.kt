package com.hikizan.hikigram.domain.reuseable

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Loading<out T>(val data: T? = null) : State<T>()
    object Empty : State<Nothing>()
    data class Error(val message: String? = null) : State<Nothing>()
}
