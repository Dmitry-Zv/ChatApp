package by.zharikov.chatapp.featurechat.data.remote

import by.zharikov.chatapp.featurechat.data.remote.dto.MessageDto
import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.util.Resource
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatSocketServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoint.ChatSocket.url}?username = $username")
            }
            if (socket?.isActive == true) Resource.Success(Unit)
            else Resource.Error("Couldn't establish a connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(msg = e.message ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter {
                    it is Frame.Text
                }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}