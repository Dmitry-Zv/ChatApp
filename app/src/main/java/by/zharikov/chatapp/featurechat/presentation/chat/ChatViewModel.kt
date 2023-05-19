package by.zharikov.chatapp.featurechat.presentation.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.featurechat.domain.repository.ChatSocketRepository
import by.zharikov.chatapp.featurechat.domain.repository.MessageRepository
import by.zharikov.chatapp.featurechat.presentation.util.UiEvent
import by.zharikov.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val chatSocketRepository: ChatSocketRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), UiEvent<ChatEvent> {


    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    override fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnMessageChanged -> changeMessage(newMessage = event.message)
            ChatEvent.Disconnect -> disconnect()
            ChatEvent.SendMessage -> sendMessage()
            ChatEvent.GetAllMessages -> getAllMessages()
            ChatEvent.Connect -> connectToChat()
            ChatEvent.Default -> performDefaultState()
        }
    }

    private fun performDefaultState() {
        _state.value = _state.value.copy(
            isLoading = false,
            error = null
        )
    }


    private fun connectToChat() {

        getAllMessages()
        savedStateHandle.get<String>("username")?.let { username ->
            viewModelScope.launch {
                when (chatSocketRepository.initSession(username)) {
                    is Resource.Success -> {
                        chatSocketRepository.observeMessages()
                            .onEach { message ->
                                val newList = _state.value.messages.toMutableList().apply {
                                    add(0, message)
                                }
                                performSuccess(messages = newList)
                            }.launchIn(viewModelScope)
                    }
                    else -> {}
                }

            }

        }
    }


    private fun getAllMessages() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val resourceMessages = messageRepository.getAllMessages()) {

                is Resource.Error -> performError(errorString = resourceMessages.msg)
                is Resource.Success -> performSuccess(messages = resourceMessages.data)

            }
        }
    }

    private fun performError(errorString: String) {
        Log.d("STATE_TAG", errorString)
        _state.value = _state.value.copy(
            isLoading = false,
            error = errorString
        )

    }

    private fun performSuccess(messages: List<Message>) {
        _state.value = _state.value.copy(
            messages = messages,
            isLoading = false,
            error = null
        )
    }

    private fun sendMessage() {
        viewModelScope.launch {
            if (messageText.value.isNotBlank()) {
                chatSocketRepository.sendMessage(messageText.value)
            }
        }
    }

    private fun disconnect() {
        viewModelScope.launch {
            chatSocketRepository.closeSession()
        }
    }

    private fun changeMessage(newMessage: String) {
        _messageText.value = newMessage
    }


    override fun onCleared() {
        super.onCleared()
        disconnect()
    }


}