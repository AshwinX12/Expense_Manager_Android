package com.expensemanager.app.ui.accounts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.dao.AccountWithBalance
import com.expensemanager.app.data.db.entity.AccountEntity
import com.expensemanager.app.data.db.entity.AccountType
import com.expensemanager.app.data.repository.AccountRepository
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

fun accountTypeLabel(type: AccountType, customTypeName: String?): String = when (type) {
    AccountType.OTHER -> customTypeName?.takeIf { it.isNotBlank() } ?: "Other"
    else -> type.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }
}

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    val accounts = accountRepository.getAllWithBalanceFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAccount(name: String, type: AccountType, customTypeName: String?, balance: BigDecimal) {
        viewModelScope.launch {
            accountRepository.insert(
                AccountEntity(
                    name = name,
                    type = type,
                    customTypeName = customTypeName,
                    initialBalance = balance
                )
            )
        }
    }

    fun updateAccount(
        account: AccountWithBalance,
        name: String,
        type: AccountType,
        customTypeName: String?,
        newCurrentBalance: BigDecimal
    ) {
        viewModelScope.launch {
            val existing = accountRepository.getById(account.id) ?: return@launch
            // The user edits the *current* balance, but what's actually stored is the
            // opening balance — back-solve it so (opening + all transactions) = what they typed.
            val transactionsNet = account.balance - account.initialBalance
            val newInitialBalance = newCurrentBalance - transactionsNet
            accountRepository.update(
                existing.copy(
                    name = name,
                    type = type,
                    customTypeName = customTypeName,
                    initialBalance = newInitialBalance
                )
            )
        }
    }

    fun deleteAccount(id: Long) {
        viewModelScope.launch {
            accountRepository.getById(id)?.let { accountRepository.delete(it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var accountToEdit by remember { mutableStateOf<AccountWithBalance?>(null) }
    var accountToDelete by remember { mutableStateOf<AccountWithBalance?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accounts", fontWeight = FontWeight.SemiBold) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = { IconButton(onClick = { showAddDialog = true }) { Icon(Icons.Default.Add, "Add") } },
                windowInsets = WindowInsets(0.dp),
                colors = flatTopAppBarColors()
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
        ) {
            items(accounts, key = { it.id }) { account ->
                var showMenu by remember { mutableStateOf(false) }
                AccountCard(
                    name = account.name,
                    balance = account.balance,
                    type = accountTypeLabel(account.type, account.customTypeName),
                    colorHex = account.colorHex,
                    onClick = { showMenu = true },
                    trailingContent = {
                        Box {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Account options")
                            }
                            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                                DropdownMenuItem(
                                    text = { Text("Edit / change balance") },
                                    leadingIcon = { Icon(Icons.Default.Edit, null) },
                                    onClick = { showMenu = false; accountToEdit = account }
                                )
                                DropdownMenuItem(
                                    text = { Text("Remove account") },
                                    leadingIcon = { Icon(Icons.Default.Delete, null) },
                                    onClick = { showMenu = false; accountToDelete = account }
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        AccountEditDialog(
            title = "Add Account",
            initialName = "",
            initialType = AccountType.BANK,
            initialCustomTypeName = "",
            initialBalance = BigDecimal.ZERO,
            onDismiss = { showAddDialog = false },
            onSave = { name, type, customTypeName, balance ->
                viewModel.addAccount(name, type, customTypeName, balance)
                showAddDialog = false
            }
        )
    }

    accountToEdit?.let { account ->
        AccountEditDialog(
            title = "Edit Account",
            initialName = account.name,
            initialType = account.type,
            initialCustomTypeName = account.customTypeName ?: "",
            initialBalance = account.balance,
            onDismiss = { accountToEdit = null },
            onSave = { name, type, customTypeName, balance ->
                viewModel.updateAccount(account, name, type, customTypeName, balance)
                accountToEdit = null
            }
        )
    }

    accountToDelete?.let { account ->
        AlertDialog(
            onDismissRequest = { accountToDelete = null },
            title = { Text("Remove ${account.name}?") },
            text = { Text("This deletes the account. Its transactions stay in your history but will show no account.") },
            confirmButton = {
                Button(onClick = { viewModel.deleteAccount(account.id); accountToDelete = null }) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { accountToDelete = null }) { Text("Cancel") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountEditDialog(
    title: String,
    initialName: String,
    initialType: AccountType,
    initialCustomTypeName: String,
    initialBalance: BigDecimal,
    onDismiss: () -> Unit,
    onSave: (name: String, type: AccountType, customTypeName: String?, balance: BigDecimal) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var type by remember { mutableStateOf(initialType) }
    var customTypeName by remember { mutableStateOf(initialCustomTypeName) }
    var balanceText by remember { mutableStateOf(initialBalance.toPlainString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                OutlinedTextField(name, { name = it }, label = { Text("Account Name") }, modifier = Modifier.fillMaxWidth())

                Text("Type", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                FlowRowChips(
                    options = AccountType.entries,
                    selected = type,
                    onSelect = { type = it }
                )

                if (type == AccountType.OTHER) {
                    OutlinedTextField(
                        customTypeName,
                        { customTypeName = it },
                        label = { Text("Type name (e.g. \"Gift Card\")") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    balanceText,
                    { balanceText = it },
                    label = { Text(if (initialName.isEmpty()) "Initial Balance" else "Current Balance") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                balanceText.toBigDecimalOrNull()?.let {
                    onSave(name, type, customTypeName.trim().ifBlank { null }, it)
                }
            }) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
private fun FlowRowChips(
    options: List<AccountType>,
    selected: AccountType,
    onSelect: (AccountType) -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingXs),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
    ) {
        options.forEach { t ->
            FilterChip(
                selected = selected == t,
                onClick = { onSelect(t) },
                label = {
                    Text(
                        if (t == AccountType.OTHER) "+ New" else accountTypeLabel(t, null),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
