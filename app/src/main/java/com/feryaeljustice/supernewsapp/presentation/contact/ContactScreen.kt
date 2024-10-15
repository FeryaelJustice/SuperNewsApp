package com.feryaeljustice.supernewsapp.presentation.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R

@Composable
fun ContactScreen(state: ContactState, onContactClick: (message: String) -> Unit) {
    val name = state.name
    val newsSourceName = state.newsSourceName
    val message = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.author, name),
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 36.dp)
        )
        Text(
            text = stringResource(R.string.source_of_news, newsSourceName),
            fontFamily = FontFamily.Monospace,
        )

        Column {
            Text(text = stringResource(R.string.contact))
            TextField(
                value = message.value,
                onValueChange = { message.value = it },
                maxLines = 1,
                placeholder = { Text(stringResource(R.string.your_message)) },
            )
            Button(onClick = { onContactClick(message.value) }) {
                Image(painter = painterResource(id = R.drawable.ic_mail), contentDescription = null)
                Text(
                    text = stringResource(R.string.sendMailMessage),
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}