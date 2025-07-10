package com.feryaeljustice.supernewsapp.presentation.contact

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1

@Composable
fun ContactScreen(
    onContactClick: (message: String) -> Unit,
    onOpenNewsSource: (link: String) -> Unit,
) {
    val viewModel: ContactViewModel = hiltViewModel()
    val state = viewModel.state.value

    val name = state.name
    val newsSourceName = state.newsSourceName
    val newsSourceLink = state.newsSourceLink
    val message = rememberSaveable { mutableStateOf("") }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    top = MediumPadding1,
                    start = MediumPadding1,
                    end = MediumPadding1,
                )
                .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = stringResource(R.string.source_of_news, newsSourceName, newsSourceLink),
                fontFamily = FontFamily.Monospace,
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(onClick = {
                onOpenNewsSource(newsSourceLink)
            }) {
                Text(
                    text = stringResource(R.string.openLink, newsSourceName),
                    fontFamily = FontFamily.Monospace,
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = stringResource(R.string.contact))
            TextField(
                value = message.value,
                onValueChange = { message.value = it },
                maxLines = 1,
                placeholder = { Text(stringResource(R.string.your_message)) },
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20),
                onClick = { onContactClick(message.value) },
            ) {
                Image(painter = painterResource(id = R.drawable.ic_mail), contentDescription = null)
                Text(
                    text = stringResource(R.string.sendMailMessage),
                    modifier = Modifier.padding(start = 2.dp),
                )
            }
        }

        Text(
            text = stringResource(R.string.author, name),
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter),
        )
    }
}
