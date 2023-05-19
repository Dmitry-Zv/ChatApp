package by.zharikov.chatapp.featurechat.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.zharikov.chatapp.featurechat.presentation.chat.ChatScreen
import by.zharikov.chatapp.featurechat.presentation.chat.ChatViewModel
import by.zharikov.chatapp.featurechat.presentation.username.UsernameScreen
import by.zharikov.chatapp.featurechat.presentation.username.UsernameViewModel
import by.zharikov.chatapp.featurechat.presentation.util.Screen

@Composable
fun NavigationApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.UsernameScreen.route) {
        composable(Screen.UsernameScreen.route) {
            val viewModel: UsernameViewModel = hiltViewModel()
            UsernameScreen(navController = navController, viewModel = viewModel)

        }
        composable(
            "${Screen.ChatScreen.route}/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("username")?.let { username ->
                val viewModel: ChatViewModel = hiltViewModel()
                ChatScreen(
                    viewModel = viewModel,
                    username = username
                )
            }

        }
    }

}