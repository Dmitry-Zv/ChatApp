package by.zharikov.chatapp.featurechat.presentation.chat

sealed class ChatEvent {
    data class OnMessageChanged(val message: String) : ChatEvent()
    object Connect : ChatEvent()
    object Disconnect : ChatEvent()
    object SendMessage : ChatEvent()
    object GetAllMessages : ChatEvent()
    object Default : ChatEvent()
}
