package by.zharikov.chatapp.featurechat.data.remote

import io.ktor.client.statement.*

interface MessageService {

    suspend fun getResponse(): HttpResponse

    companion object {
        const val BASE_URL = "http://192.168.56.1:8080"
    }

    sealed class Endpoint(val url: String) {
        object GetAllMessages : Endpoint("$BASE_URL/messages")
    }
}