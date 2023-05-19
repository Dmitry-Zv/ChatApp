package by.zharikov.chatapp.featurechat.presentation.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.zharikov.chatapp.featurechat.domain.model.Message

@Composable
fun MessageComponent(
    modifier: Modifier = Modifier,
    isOwnMessage: Boolean,
    message: Message

) {

    Box(
        modifier = modifier,
        contentAlignment = if (isOwnMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .drawBehind {
                    val cornerRadius = 10.dp.toPx()
                    val triangleHeight = 20.dp.toPx()
                    val triangleWidth = 25.dp.toPx()
                    val trianglePath = Path().apply {
                        if (isOwnMessage) {
                            moveTo(x = size.width, y = size.height - cornerRadius)
                            lineTo(x = size.width, y = size.height + triangleHeight)
                            lineTo(x = size.width - triangleWidth, y = size.height - cornerRadius)
                            close()
                        } else {
                            moveTo(x = 0f, y = size.height - cornerRadius)
                            lineTo(x = 0f, y = size.height + triangleHeight)
                            lineTo(x = triangleWidth, y = size.height - cornerRadius)
                            close()
                        }
                    }
                    drawPath(
                        path = trianglePath,
                        color = if (isOwnMessage) Color.Blue else Color.DarkGray
                    )

                }
                .background(
                    color = if (isOwnMessage) Color.Blue else Color.DarkGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.username,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = message.text,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = message.formattedTime,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.End)
            )

        }
    }

}