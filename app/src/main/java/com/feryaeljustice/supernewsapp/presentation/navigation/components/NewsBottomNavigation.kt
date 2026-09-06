package com.feryaeljustice.supernewsapp.presentation.navigation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedItem
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.text,
                        modifier = Modifier.size(20.dp),
                    )
                },
                label = {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                ),
            )
        }
    }
}

data class BottomNavigationItem(
    @param:DrawableRes val icon: Int,
    val text: String,
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    val homeText = stringResource(R.string.home)
    val searchText = stringResource(R.string.search)
    val bookmarkText = stringResource(R.string.bookmark)

    SuperNewsAppTheme(dynamicColor = false) {
        NewsBottomNavigation(
            items =
                listOf(
                    BottomNavigationItem(icon = R.drawable.ic_home, text = homeText),
                    BottomNavigationItem(icon = R.drawable.ic_search, text = searchText),
                    BottomNavigationItem(icon = R.drawable.ic_bookmark, text = bookmarkText),
                ),
            selectedItem = 0,
            onItemClick = {},
        )
    }
}
