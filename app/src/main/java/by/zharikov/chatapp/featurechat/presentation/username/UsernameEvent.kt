package by.zharikov.chatapp.featurechat.presentation.username

sealed class UsernameEvent {
    data class OnUsernameChanged(val username: String) : UsernameEvent()
    object OnJoinClicked : UsernameEvent()
}