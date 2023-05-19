package by.zharikov.chatapp.featurechat.data.remote

import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()


    companion object {
        const val BASE_URL = "ws://192.168.56.1:8080"
    }

    sealed class Endpoint(val url: String) {
        object ChatSocket : Endpoint("$BASE_URL/chat-socket")
    }
}