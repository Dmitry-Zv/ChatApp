package by.zharikov.chatapp.featurechat.data.remote

import io.ktor.client.statement.*

interface MessageService {

    suspend fun getResponse(): HttpResponse

    companion object {
        const val BASE_URL = "http://172.20.10.3:8080"
    }

    sealed class Endpoint(val url: String) {
        object GetAllMessages : Endpoint("$BASE_URL/messages")
    }
}