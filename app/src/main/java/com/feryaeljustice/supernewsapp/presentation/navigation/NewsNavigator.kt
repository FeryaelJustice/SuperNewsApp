package com.feryaeljustice.supernewsapp.presentation.navigation

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.presentation.bookmark.BookmarkScreen
import com.feryaeljustice.supernewsapp.presentation.contact.ContactScreen
import com.feryaeljustice.supernewsapp.presentation.home.HomeScreen
import com.feryaeljustice.supernewsapp.presentation.navigation.components.AnalyticsPanel
import com.feryaeljustice.supernewsapp.presentation.navigation.components.BottomNavigationItem
import com.feryaeljustice.supernewsapp.presentation.navigation.components.CustomNavigationRail
import com.feryaeljustice.supernewsapp.presentation.navigation.components.NewsBottomNavigation
import com.feryaeljustice.supernewsapp.presentation.newsDetail.DetailsScreen
import com.feryaeljustice.supernewsapp.presentation.search.SearchScreen
import com.feryaeljustice.supernewsapp.ui.adaptive.DeviceType
import com.feryaeljustice.supernewsapp.ui.adaptive.LocalDeviceType
import com.feryaeljustice.supernewsapp.ui.adaptive.greaterThanOrEqual

@Composable
fun NewsNavigator(navigatorViewModel: NavigationViewModel = viewModel()) {
    val deviceType = LocalDeviceType.current

    val homeText = stringResource(R.string.home)
    val searchText = stringResource(R.string.search)
    val bookmarkText = stringResource(R.string.bookmark)
    val contactText = stringResource(R.string.contact)

    val bottomNavigationItems =
        remember {
            listOf(
                BottomNavigationItem(icon = R.drawable.ic_home, text = homeText),
                BottomNavigationItem(icon = R.drawable.ic_search, text = searchText),
                BottomNavigationItem(icon = R.drawable.ic_bookmark, text = bookmarkText),
                BottomNavigationItem(icon = R.drawable.ic_contact, text = contactText),
            )
        }
    // Inicializamos el contenedor interno apuntando a Home si la pila está vacía
    LaunchedEffect(navigatorViewModel) {
        navigatorViewModel.initialize(Route.HomeScreen)
    }

    // Calculamos de manera reactiva cuál es la pantalla activa actual en la cima de la pila
    val currentRoute by remember {
        derivedStateOf { navigatorViewModel.backStack.lastOrNull() }
    }

    val selectedItem = when (currentRoute) {
        is Route.HomeScreen -> 0
        is Route.SearchScreen -> 1
        is Route.BookmarkScreen -> 2
        is Route.ContactScreen -> 3
        else -> 0
    }

    // Hide the bottom navigation when the user is in the details screen
    val areBarsVisible =
        currentRoute is Route.NewsNavigation || currentRoute is Route.NewsNavigatorScreen || currentRoute is Route.HomeScreen ||
                currentRoute is Route.SearchScreen ||
                currentRoute is Route.BookmarkScreen ||
                currentRoute is Route.ContactScreen

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (areBarsVisible && (deviceType is DeviceType.Compact)) {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 ->
                            navigateToTab(
                                navigatorViewModel = navigatorViewModel,
                                route = Route.HomeScreen,
                            )

                        1 ->
                            navigateToTab(
                                navigatorViewModel = navigatorViewModel,
                                route = Route.SearchScreen,
                            )

                        2 ->
                            navigateToTab(
                                navigatorViewModel = navigatorViewModel,
                                route = Route.BookmarkScreen,
                            )

                        3 ->
                            navigateToTab(
                                navigatorViewModel = navigatorViewModel,
                                route = Route.ContactScreen,
                            )
                    }
                },
            )
        }
    }) { paddingValues ->
        val bottomPadding = paddingValues.calculateBottomPadding()

        if (deviceType greaterThanOrEqual DeviceType.Medium) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomPadding)
            ) {
                AnimatedVisibility(areBarsVisible) { CustomNavigationRail() }
                CommonNavHost(
                    navigatorViewModel = navigatorViewModel,
                    paddingValues = paddingValues
                )
                AnimatedVisibility(areBarsVisible && deviceType greaterThanOrEqual DeviceType.Expanded) {
                    AnalyticsPanel(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(260.dp)
                    )
                }
            }
        } else {
            CommonNavHost(
                navigatorViewModel = navigatorViewModel,
                paddingValues = PaddingValues(bottom = bottomPadding)
            )
        }
    }
}

@Composable
fun CommonNavHost(navigatorViewModel: NavigationViewModel, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val emptyMsgNotAllowed = stringResource(R.string.emptyMsgNotAllowed)
    val contactMsgLengthWarning = stringResource(R.string.contactMsgLengthWarning)
    val contactToEmail = stringResource(R.string.contact_to_email)
    val contactToUser = stringResource(R.string.contact_from_user)

    // Interceptamos la navegación hacia atrás nativa de los botones del dispositivo
    if (navigatorViewModel.backStack.lastOrNull() !is Route.HomeScreen) {
        BackHandler(enabled = true) {
            val processed = navigatorViewModel.popBackStack()
            if (!processed) {
                navigatorViewModel.navigateToTab(Route.HomeScreen)
            }
        }
    }

    NavDisplay(
        backStack = navigatorViewModel.backStack,
        onBack = {},
        modifier = Modifier.padding(paddingValues),
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator())
    ) { route ->
        when (route) {
            is Route.HomeScreen, Route.NewsNavigation, Route.NewsNavigatorScreen -> NavEntry(route) {
                HomeScreen(
                    navigateToDetails = { article ->
                        navigatorViewModel.navigateTo(Route.NewsDetailScreen(article))
                    }
                )
            }

            is Route.ContactScreen -> NavEntry(route) {
                ContactScreen(
                    onContactClick = { message ->
                        if (message.isBlank() || message.isEmpty()) {
                            Toast.makeText(context, emptyMsgNotAllowed, Toast.LENGTH_SHORT).show()
                            return@ContactScreen
                        }
                        if (message.length > 100) {
                            Toast.makeText(context, contactMsgLengthWarning, Toast.LENGTH_SHORT)
                                .show()
                            return@ContactScreen
                        }

                        val mailIntent = Intent(Intent.ACTION_SEND).apply {
                            data = "mailto:".toUri()
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(contactToEmail))
                            putExtra(Intent.EXTRA_SUBJECT, contactToUser)
                            putExtra(Intent.EXTRA_TEXT, message)
                        }
                        try {
                            context.startActivity(mailIntent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    onOpenNewsSource = { link ->
                        uriHandler.openUri(link)
                    },
                )
            }

            is Route.SearchScreen -> NavEntry(route) {
                OnBackClickStateSaver(navigatorViewModel = navigatorViewModel)
                SearchScreen(
                    navigateToDetails = { article ->
                        navigatorViewModel.navigateTo(Route.NewsDetailScreen(article))
                    },
                )
            }

            is Route.NewsDetailScreen -> NavEntry(route) {
                // Recuperamos de manera completamente automatizada y segura el artículo tipado de la ruta
                DetailsScreen(
                    article = route.article,
                    navigateUp = { navigatorViewModel.popBackStack() },
                )
            }

            is Route.BookmarkScreen -> NavEntry(route) {
                OnBackClickStateSaver(navigatorViewModel = navigatorViewModel)
                BookmarkScreen(
                    navigateToDetails = { article ->
                        navigatorViewModel.navigateTo(Route.NewsDetailScreen(article))
                    },
                    navigateToHome = {
                        navigatorViewModel.navigateToTab(Route.HomeScreen)
                    }
                )
            }

            else -> NavEntry(route) {}
        }
    }
}

@Composable
fun OnBackClickStateSaver(navigatorViewModel: NavigationViewModel) {
    BackHandler(enabled = true) {
        // Si el usuario no está en la HomeScreen, la pestaña inferior se redirige a ella limpiando la pila
        navigatorViewModel.navigateToTab(Route.HomeScreen)
    }
}

private fun navigateToTab(
    navigatorViewModel: NavigationViewModel,
    route: Route,
) {
    navigatorViewModel.navigateToTab(route)
}