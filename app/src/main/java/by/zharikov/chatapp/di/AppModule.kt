package by.zharikov.chatapp.di

import by.zharikov.chatapp.featurechat.data.remote.ChatSocketService
import by.zharikov.chatapp.featurechat.data.remote.ChatSocketServiceImpl
import by.zharikov.chatapp.featurechat.data.remote.MessageService
import by.zharikov.chatapp.featurechat.data.remote.MessageServiceImpl
import by.zharikov.chatapp.featurechat.data.repository.ChatSocketRepositoryImpl
import by.zharikov.chatapp.featurechat.data.repository.MessageRepositoryImpl
import by.zharikov.chatapp.featurechat.domain.repository.ChatSocketRepository
import by.zharikov.chatapp.featurechat.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
        }
}

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    @Singleton
    fun bindChatSocketService_toChatSocketServiceImpl(chatSocketServiceImpl: ChatSocketServiceImpl): ChatSocketService

    @Binds
    @Singleton
    fun bindMessageService_toMessageServiceImpl(messageServiceImpl: MessageServiceImpl): MessageService

    @Binds
    @Singleton
    fun bindChatSocketRepository_toChatSocketRepositoryImpl(chatSocketRepositoryImpl: ChatSocketRepositoryImpl): ChatSocketRepository

    @Binds
    @Singleton
    fun bindMessageRepository_toMessageRepositoryImpl(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository
}