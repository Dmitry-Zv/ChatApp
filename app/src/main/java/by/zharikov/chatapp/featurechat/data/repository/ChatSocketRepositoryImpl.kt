package by.zharikov.chatapp.featurechat.data.repository

import by.zharikov.chatapp.featurechat.data.remote.ChatSocketService
import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.featurechat.domain.repository.ChatSocketRepository
import by.zharikov.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatSocketRepositoryImpl @Inject constructor(private val chatSocketService: ChatSocketService) :
    ChatSocketRepository {

    override suspend fun initSession(username: String): Resource<Unit> =
        chatSocketService.initSession(username)

    override suspend fun sendMessage(message: String) {
        chatSocketService.sendMessage(message)
    }

    override suspend fun observeMessages(): Flow<Message> =
        chatSocketService.observeMessages()


    override suspend fun closeSession() {
        chatSocketService.closeSession()
    }
}