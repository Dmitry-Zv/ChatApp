package by.zharikov.chatapp.featurechat.presentation.chat

import by.zharikov.chatapp.featurechat.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
