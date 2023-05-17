package by.zharikov.chatapp.featurechat.data.remote.dto

@kotlinx.serialization.Serializable
data class MessageDto(
    val text: String,
    val username: String,
    val timestamp: Long,
    val id: String
)