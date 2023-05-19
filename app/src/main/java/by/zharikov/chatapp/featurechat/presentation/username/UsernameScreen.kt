package by.zharikov.chatapp.featurechat.presentation.username

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.zharikov.chatapp.featurechat.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel,
    navController: NavController

) {

    val usernameState = viewModel.usernameText.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onJointChat.collectLatest { state ->
            when {
                state.username.isBlank() && state.error != null -> {
                    scaffoldState.snackbarHostState.showSnackbar(state.error)
                }
                state.username.isNotBlank() && state.error == null -> {
                    navController.navigate("${Screen.ChatScreen.route}/${state.username}")
                }
            }

        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usernameState,
                    onValueChange = {
                        viewModel.onEvent(event = UsernameEvent.OnUsernameChanged(username = it))
                    }, placeholder = {
                        Text(
                            text = "Enter your username...",
                            fontSize = 20.sp,
                        )
                    },
                    singleLine = true,
                    maxLines = 1
                )

                Button(
                    onClick = {
                        viewModel.onEvent(event = UsernameEvent.OnJoinClicked)
                        viewModel.onEvent(event = UsernameEvent.OnUsernameChanged(""))
                    },
                    modifier = Modifier.padding(top = 10.dp),
                ) {
                    Text(
                        text = "Join to chat",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


            }
        }
    }


}