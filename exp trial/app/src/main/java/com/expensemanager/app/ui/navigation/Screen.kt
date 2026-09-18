package com.expensemanager.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    // Bottom nav destinations
    data object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Transactions : Screen("transactions", "Transactions", Icons.Filled.Receipt, Icons.Outlined.Receipt)
    data object Planning : Screen("planning", "Planning", Icons.Filled.PieChart, Icons.Outlined.PieChart)
    data object Reports : Screen("reports", "Reports", Icons.Filled.BarChart, Icons.Outlined.BarChart)
    data object Lending : Screen("lending", "Lending", Icons.Filled.People, Icons.Outlined.People)

    // Non-nav screens
    data object AddTransaction : Screen("add_transaction", "Add")
    data object EditTransaction : Screen("edit_transaction/{transactionId}", "Edit") {
        fun withId(id: Long) = "edit_transaction/$id"
    }
    data object Settings : Screen("settings", "Settings")
    data object Accounts : Screen("accounts", "Accounts")
    data object Categories : Screen("categories", "Categories")
    // Goals removed (now inside Planning)
    data object AccountDetail : Screen("account_detail/{accountId}", "Account") {
        fun withId(id: Long) = "account_detail/$id"
    }

    companion object {
        val bottomNavItems: List<Screen>
            get() = listOf(Home, Transactions, Planning, Reports, Lending)
    }

}
