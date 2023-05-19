package by.zharikov.chatapp.featurechat.presentation.util

interface UiEvent<E> {

    fun onEvent(event: E)

}