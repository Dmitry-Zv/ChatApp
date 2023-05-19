package by.zharikov.chatapp.featurechat.presentation.util

sealed class Screen(val route: String) {
    object UsernameScreen : Screen("username_screen")
    object ChatScreen : Screen("chat_screen")
}
