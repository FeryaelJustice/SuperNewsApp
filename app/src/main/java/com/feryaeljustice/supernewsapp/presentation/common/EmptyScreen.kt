package com.feryaeljustice.supernewsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    emptyMessage: String? = null,
    actionButtonText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    val defaultErrorMessage = stringResource(R.string.network_error_desc)
    val defaultEmptyMessage = stringResource(R.string.notSavedNews)

    val message = emptyMessage ?: if (error != null) {
        parseErrorMessage(error = error, defaultMessage = defaultErrorMessage)
    } else {
        defaultEmptyMessage
    }

    val icon = if (error != null) R.drawable.ic_network_error else R.drawable.ic_search_document

    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "emptyAlpha",
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        alphaAnim = alphaAnimation,
        message = message,
        iconId = icon,
        actionButtonText = actionButtonText ?: if (error != null) stringResource(R.string.retry) else null,
        onActionClick = onActionClick,
    )
}

@Composable
fun EmptyContent(
    alphaAnim: Float,
    message: String,
    iconId: Int,
    actionButtonText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f * alphaAnim),
            modifier = Modifier
                .size(96.dp)
                .alpha(alphaAnim),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        if (actionButtonText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onActionClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.alpha(alphaAnim)
            ) {
                Text(
                    text = actionButtonText,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

fun parseErrorMessage(error: LoadState.Error?, defaultMessage: String): String =
    when (error?.error) {
        is SocketTimeoutException, is ConnectException -> defaultMessage
        else -> defaultMessage
    }

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    SuperNewsAppTheme {
        EmptyContent(
            alphaAnim = 1f,
            message = "No tienes artículos guardados aún",
            iconId = R.drawable.ic_search_document,
            actionButtonText = "Explorar noticias",
            onActionClick = {}
        )
    }
}
