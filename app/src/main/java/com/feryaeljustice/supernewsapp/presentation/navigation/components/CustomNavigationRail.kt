package com.feryaeljustice.supernewsapp.presentation.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.feryaeljustice.supernewsapp.presentation.navigation.Destination
import com.feryaeljustice.supernewsapp.presentation.navigation.NavigationViewModel

@Composable
fun CustomNavigationRail(
    modifier: Modifier = Modifier,
    navigatorViewModel: NavigationViewModel = viewModel()
) {
    // Calculamos de manera reactiva qué elemento del enum coincide con la ruta activa actual
    val currentRoute = navigatorViewModel.backStack.lastOrNull()
    val selectedDestination by remember(currentRoute) {
        derivedStateOf {
            Destination.entries.firstOrNull { it.route == currentRoute } ?: Destination.Home
        }
    }

    NavigationRail(modifier = modifier) {
        Destination.entries.forEach { destination ->
            NavigationRailItem(
                selected = selectedDestination == destination,
                onClick = {
                    // Limpia la pila y añade la nueva pestaña de forma segura
                    navigatorViewModel.navigateToTab(destination.route)
                },
                icon = {
                    Icon(
                        painterResource(destination.icon),
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(destination.label) }
            )
        }
    }
}