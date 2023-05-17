package by.zharikov.chatapp.util

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val msg: String) : Resource<Nothing>()
}