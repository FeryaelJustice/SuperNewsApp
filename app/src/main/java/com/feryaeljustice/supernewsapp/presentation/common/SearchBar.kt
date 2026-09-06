package com.feryaeljustice.supernewsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val placeholderText = stringResource(R.string.search_placeholder)

    val interactionSource = remember { MutableInteractionSource() }
    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            readOnly = readOnly,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(R.string.clear_search),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onValueChange("") },
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                )
            },
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearch()
                },
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            interactionSource = interactionSource,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    SuperNewsAppTheme {
        SearchBar(text = "Climate change", onValueChange = {}, readOnly = false) {}
    }
}
