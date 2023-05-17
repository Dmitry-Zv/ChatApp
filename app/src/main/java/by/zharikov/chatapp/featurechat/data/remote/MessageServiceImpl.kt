package by.zharikov.chatapp.featurechat.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(
    private val client: HttpClient
) : MessageService {
    override suspend fun getResponse(): HttpResponse =
        client.get(MessageService.Endpoint.GetAllMessages.url)
}