package by.zharikov.chatapp.featurechat.domain.repository

import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketRepository {

    suspend fun initSession(username: String): Resource<Unit>

    suspend fun sendMessage(message:String)

    suspend fun observeMessages():Flow<Message>

    suspend fun closeSession()
}