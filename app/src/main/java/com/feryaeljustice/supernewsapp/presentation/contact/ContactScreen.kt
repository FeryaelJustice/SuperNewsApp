/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.presentation.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R

@Composable
fun ContactScreen(state: ContactState, onContactClick: (message: String) -> Unit) {
    val name = state.name
    val message = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Author: $name",
            color = Color.White,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 36.dp)
        )

        Column {
            Text(text = "Contact me")
            TextField(
                value = message.value,
                onValueChange = { message.value = it },
                maxLines = 1,
                placeholder = { Text("Your message") },
            )
            Button(onClick = { onContactClick(message.value) }) {
                Image(painter = painterResource(id = R.drawable.ic_mail), contentDescription = null)
                Text(
                    text = "Open your mail app and send me a message",
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}