package by.zharikov.chatapp.featurechat.presentation.chat.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SendMessageComponent(
    modifier: Modifier = Modifier,
    messageText: String,
    onMessageTextFieldChanged: (String) -> Unit,
    onSendMessage: () -> Unit

) {

    Row(modifier = modifier) {
        TextField(
            value = messageText,
            onValueChange = onMessageTextFieldChanged,
            placeholder = {
                Text(text = "Enter a message...")
            },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSendMessage) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send message"
            )
        }

    }

}