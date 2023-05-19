package by.zharikov.chatapp.featurechat.presentation.chat

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import by.zharikov.chatapp.featurechat.presentation.chat.component.MessageComponent
import by.zharikov.chatapp.featurechat.presentation.chat.component.SendMessageComponent

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    username: String
) {

    val messageStateText = viewModel.messageText.value
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = state) {
        when (val error = state.error) {
            is String -> {
                scaffoldState.snackbarHostState.showSnackbar(error)
                viewModel.onEvent(event = ChatEvent.Default)
            }
            null -> {}
        }
        Log.d("STATE_TAG", state.toString())
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onEvent(event = ChatEvent.Connect)

            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onEvent(event = ChatEvent.Disconnect)
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                items(state.messages) { message ->

                    val isOwnMessage = message.username == username

                    MessageComponent(
                        isOwnMessage = isOwnMessage,
                        message = message,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                }
            }
            SendMessageComponent(messageText = messageStateText,
                onMessageTextFieldChanged = { value ->
                    viewModel.onEvent(event = ChatEvent.OnMessageChanged(message = value))

                },
                onSendMessage = {
                    viewModel.onEvent(event = ChatEvent.SendMessage)
                    viewModel.onEvent(event = ChatEvent.OnMessageChanged(message = ""))
                })
        }


    }

}