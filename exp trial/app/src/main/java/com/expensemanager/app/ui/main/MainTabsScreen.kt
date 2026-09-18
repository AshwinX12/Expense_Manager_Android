package com.expensemanager.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.expensemanager.app.ui.reports.ReportsScreen
import com.expensemanager.app.ui.lending.LendingScreen
import com.expensemanager.app.ui.home.HomeScreen
import com.expensemanager.app.ui.navigation.Screen
import com.expensemanager.app.ui.planning.PlanningScreen
import com.expensemanager.app.ui.theme.Dimens
import com.expensemanager.app.ui.transactions.TransactionsScreen
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.dp
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTabsScreen(
    navController: NavController,
    onReselect: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { Screen.bottomNavItems.size })
    val coroutineScope = rememberCoroutineScope()

    androidx.activity.compose.BackHandler(enabled = pagerState.currentPage != 0) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(0)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp), // Prevent double top padding
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = Dimens.BottomBarElevation
            ) {
                Screen.bottomNavItems.forEachIndexed { index, screen ->
                    val selected = pagerState.currentPage == index

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
                                onReselect(screen.route)
                            } else {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddTransaction.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            beyondViewportPageCount = 1 // Keep a few pages alive
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (Screen.bottomNavItems[page].route) {
                    Screen.Home.route -> {
                        HomeScreen(
                            onNavigateToTransactions = {
                                coroutineScope.launch { pagerState.animateScrollToPage(1) } // Assuming 1 is Transactions
                            },
                            onNavigateToAdd = { navController.navigate(Screen.AddTransaction.route) },
                            onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                            onNavigateToReports = { navController.navigate(Screen.Reports.route) },
                            onTransactionClick = { id -> navController.navigate(Screen.EditTransaction.withId(id)) }
                        )
                    }
                    Screen.Transactions.route -> {
                        TransactionsScreen(
                            onTransactionClick = { id -> navController.navigate(Screen.EditTransaction.withId(id)) },
                            onAddClick = { navController.navigate(Screen.AddTransaction.route) }
                        )
                    }
                    Screen.Planning.route -> {
                        PlanningScreen()
                    }
                    Screen.Reports.route -> {
                        ReportsScreen()
                    }
                    Screen.Lending.route -> {
                        LendingScreen(
                            onNavigateBack = { /* Ignored in pager */ }
                        )
                    }
                }
            }
        }
    }
}
