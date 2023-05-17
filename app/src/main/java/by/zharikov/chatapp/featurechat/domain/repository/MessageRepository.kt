package by.zharikov.chatapp.featurechat.domain.repository

import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.util.Resource

interface MessageRepository {


    suspend fun getAllMessages():Resource<List<Message>>
}