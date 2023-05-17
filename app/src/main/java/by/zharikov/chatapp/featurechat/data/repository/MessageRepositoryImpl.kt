package by.zharikov.chatapp.featurechat.data.repository

import by.zharikov.chatapp.featurechat.data.remote.MessageService
import by.zharikov.chatapp.featurechat.data.remote.dto.MessageDto
import by.zharikov.chatapp.featurechat.domain.model.Message
import by.zharikov.chatapp.featurechat.domain.repository.MessageRepository
import by.zharikov.chatapp.util.Resource
import io.ktor.client.call.*
import io.ktor.http.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val messageService: MessageService) : MessageRepository {

    override suspend fun getAllMessages(): Resource<List<Message>> {
        return try {
            val response = messageService.getResponse()
            val status = response.status
            if (status == HttpStatusCode.OK) {
                val messagesDto = response.body<List<MessageDto>>()
                val messages = messagesDto.map { it.toMessage() }
                Resource.Success(data = messages)
            } else Resource.Error(msg = "Error: HttpStatus: $status")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(msg = e.message ?: "Unknown error")
        }
    }
}