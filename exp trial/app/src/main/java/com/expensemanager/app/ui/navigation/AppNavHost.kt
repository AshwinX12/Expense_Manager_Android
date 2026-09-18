package com.expensemanager.app.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.expensemanager.app.ui.main.MainTabsScreen
import com.expensemanager.app.ui.home.HomeScreen
import com.expensemanager.app.ui.add.AddTransactionScreen
import com.expensemanager.app.ui.transactions.TransactionsScreen
import com.expensemanager.app.ui.planning.PlanningScreen
import com.expensemanager.app.ui.reports.ReportsScreen
import com.expensemanager.app.ui.settings.SettingsScreen
import com.expensemanager.app.ui.accounts.AccountsScreen
import com.expensemanager.app.ui.categories.CategoriesScreen
import com.expensemanager.app.ui.lending.LendingScreen
import com.expensemanager.app.ui.theme.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

val LocalNavReselectEvent = compositionLocalOf<SharedFlow<String>> { MutableSharedFlow() }

@Composable
fun AppNavHost(
    isSwipeNavigationEnabled: Boolean = true,
    openAddTransaction: Boolean = false,
    onAddTransactionHandled: () -> Unit = {}
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Launched from the widget's + button
    LaunchedEffect(openAddTransaction) {
        if (openAddTransaction) {
            navController.navigate(Screen.AddTransaction.route) { launchSingleTop = true }
            onAddTransactionHandled()
        }
    }

    val showBottomBar = !isSwipeNavigationEnabled && currentRoute in Screen.bottomNavItems.map { it.route }

    val navReselectChannel = remember { 
        MutableSharedFlow<String>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        ) 
    }

    CompositionLocalProvider(LocalNavReselectEvent provides navReselectChannel) {
        Scaffold(
            bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = Dimens.BottomBarElevation
                ) {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = navBackStackEntry?.destination?.hierarchy?.any {
                            it.route == screen.route
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon!! else screen.unselectedIcon!!,
                                    contentDescription = screen.title
                                )
                            },
                            label = { 
                                Text(
                                    text = screen.title, 
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis
                                ) 
                            },
                            selected = selected,
                            onClick = {
                                if (selected) {
                                    navReselectChannel.tryEmit(screen.route)
                                } else {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddTransaction.route) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.large
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (isSwipeNavigationEnabled) "main_tabs" else Screen.Home.route,
            modifier = Modifier.padding(paddingValues),
            enterTransition = {
                // Push screens slide in from right
                slideInHorizontally(
                    initialOffsetX = { it / 3 },
                    animationSpec = tween(300)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it / 6 },
                    animationSpec = tween(300)
                ) + fadeOut(tween(200))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it / 6 },
                    animationSpec = tween(300)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it / 3 },
                    animationSpec = tween(300)
                ) + fadeOut(tween(200))
            }
        ) {
            composable("main_tabs") {
                MainTabsScreen(
                    navController = navController,
                    onReselect = { route -> navReselectChannel.tryEmit(route) }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToTransactions = {
                        navController.navigate(Screen.Transactions.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToAdd = { navController.navigate(Screen.AddTransaction.route) },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onNavigateToReports = {
                        navController.navigate(Screen.Reports.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onTransactionClick = { id ->
                        navController.navigate(Screen.EditTransaction.withId(id))
                    }
                )
            }

            composable(route = Screen.AddTransaction.route) {
                AddTransactionScreen(
                    onNavigateBack = { navController.popBackStack() },
                    transactionId = null
                )
            }

            composable(
                route = Screen.EditTransaction.route,
                arguments = listOf(navArgument("transactionId") { type = NavType.LongType })
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getLong("transactionId")
                AddTransactionScreen(
                    onNavigateBack = { navController.popBackStack() },
                    transactionId = transactionId
                )
            }

            composable(Screen.Transactions.route) {
                TransactionsScreen(
                    onTransactionClick = { id ->
                        navController.navigate(Screen.EditTransaction.withId(id))
                    },
                    onAddClick = { navController.navigate(Screen.AddTransaction.route) }
                )
            }

            composable(Screen.Planning.route) {
                PlanningScreen()
            }

            composable(Screen.Reports.route) {
                ReportsScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAccounts = { navController.navigate(Screen.Accounts.route) },
                    onNavigateToCategories = { navController.navigate(Screen.Categories.route) }
                )
            }

            composable(Screen.Accounts.route) {
                AccountsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Categories.route) {
                CategoriesScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Lending.route) {
                LendingScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        }
    }
}
