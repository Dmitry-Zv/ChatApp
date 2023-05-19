package by.zharikov.chatapp.featurechat.presentation.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.zharikov.chatapp.featurechat.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor() : ViewModel(), UiEvent<UsernameEvent> {

    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _onJoinChat = MutableSharedFlow<UsernameState>()
    val onJointChat = _onJoinChat.asSharedFlow()

    override fun onEvent(event: UsernameEvent) {
        when (event) {
            is UsernameEvent.OnUsernameChanged -> changeUsername(username = event.username)
            UsernameEvent.OnJoinClicked -> onJoinClicked()
        }
    }

    private fun onJoinClicked() {
        viewModelScope.launch {
            if (usernameText.value.isNotBlank()) _onJoinChat.emit(
                UsernameState(
                    username = usernameText.value,
                    error = null
                )
            )
            else _onJoinChat.emit(
                UsernameState(
                    username = "",
                    error = "Username is blank!"
                )
            )
        }
    }

    private fun changeUsername(username: String) {
        _usernameText.value = username
    }
}