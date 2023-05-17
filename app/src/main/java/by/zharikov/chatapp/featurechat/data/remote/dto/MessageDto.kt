package by.zharikov.chatapp.featurechat.data.remote.dto

import by.zharikov.chatapp.featurechat.domain.model.Message
import java.text.DateFormat
import java.util.Date

@kotlinx.serialization.Serializable
data class MessageDto(
    val text: String,
    val username: String,
    val timestamp: Long,
    val id: String
) {
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat.getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            text = text,
            username = username,
            formattedTime = formattedDate
        )
    }
}