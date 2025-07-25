package com.feryaeljustice.supernewsapp.presentation.newsDetail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    isBookmarked: Boolean = false,
) {
    TopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        colors =
            TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                actionIconContentColor = colorResource(id = R.color.iconTint),
                navigationIconContentColor = colorResource(id = R.color.iconTint),
            ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    painter =
                        if (isBookmarked) {
                            painterResource(id = R.drawable.ic_bookmark_added)
                        } else {
                            painterResource(
                                id = R.drawable.ic_bookmark,
                            )
                        },
                    contentDescription = null,
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = onBrowsingClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview() {
    SuperNewsAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            DetailsTopBar(
                onBrowsingClick = {},
                onShareClick = {},
                onBookmarkClick = {},
                onBackClick = {},
            )
        }
    }
}
