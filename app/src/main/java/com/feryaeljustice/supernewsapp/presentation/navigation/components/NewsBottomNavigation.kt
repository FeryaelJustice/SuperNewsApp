package com.feryaeljustice.supernewsapp.presentation.navigation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding2
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize),
                        )
                        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                    }
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = colorResource(id = R.color.iconTint),
                        unselectedTextColor = colorResource(id = R.color.iconTint),
                        indicatorColor = MaterialTheme.colorScheme.background,
                    ),
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
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
