package com.feryaeljustice.supernewsapp.presentation.navigation

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.bookmark.BookmarkScreen
import com.feryaeljustice.supernewsapp.presentation.bookmark.BookmarkViewModel
import com.feryaeljustice.supernewsapp.presentation.contact.ContactScreen
import com.feryaeljustice.supernewsapp.presentation.contact.ContactViewModel
import com.feryaeljustice.supernewsapp.presentation.newsDetail.DetailsScreen
import com.feryaeljustice.supernewsapp.presentation.newsDetail.NewsDetailScreenViewModel
import com.feryaeljustice.supernewsapp.presentation.home.HomeScreen
import com.feryaeljustice.supernewsapp.presentation.home.HomeViewModel
import com.feryaeljustice.supernewsapp.presentation.navigation.components.BottomNavigationItem
import com.feryaeljustice.supernewsapp.presentation.navigation.components.NewsBottomNavigation
import com.feryaeljustice.supernewsapp.presentation.search.SearchScreen
import com.feryaeljustice.supernewsapp.presentation.search.SearchViewModel


@Composable
fun NewsNavigator() {
    val context = LocalContext.current

    val homeText = stringResource(R.string.home)
    val searchText = stringResource(R.string.search)
    val bookmarkText = stringResource(R.string.bookmark)

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = homeText),
            BottomNavigationItem(icon = R.drawable.ic_search, text = searchText),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = bookmarkText),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.BookmarkScreen.route
                        )
                    }
                }
            )
        }
    }) { paddingValues ->
        val bottomPadding = paddingValues.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
//                    navigateToSearch = {
//                        navigateToTab(
//                            navController = navController,
//                            route = Route.SearchScreen.route
//                        )
//                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    },
                    navigateToContact = {
                        navigateToTab(
                            navController = navController,
                            route = Route.ContactScreen.route
                        )
                    },
                    event = viewModel::onEvent,
                    state = viewModel.state.value
                )
            }
            composable(route = Route.ContactScreen.route) {
                val viewModel: ContactViewModel = hiltViewModel()
                val state = viewModel.state.value
                ContactScreen(
                    state = state,
                    onContactClick = { message ->
                        if (message.isBlank() || message.isEmpty()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.emptyMsgNotAllowed),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            return@ContactScreen
                        }
                        if (message.length > 100) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.contactMsgLengthWarning),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            return@ContactScreen
                        }

                        val mailIntent = Intent(Intent.ACTION_SEND)
                        // type: text/plain
                        mailIntent.setDataAndType(Uri.parse("mailto:"), "message/rfc822")
                        mailIntent.putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(context.getString(R.string.contact_to_email))
                        )
                        mailIntent.putExtra(Intent.EXTRA_SUBJECT,
                            context.getString(R.string.contact_from_user))
                        mailIntent.putExtra(Intent.EXTRA_TEXT, message)
                        try {
                            context.startActivity(
                                Intent.createChooser(
                                    mailIntent,
                                    context.getString(R.string.chooseMailClient)
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    })
            }
            composable(route = Route.NewsDetailScreen.route) {
                val viewModel: NewsDetailScreenViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            viewModel = viewModel,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = viewModel.sideEffect
                        )
                    }

            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.NewsDetailScreen.route
    )
}