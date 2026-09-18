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

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    val accounts = accountRepository.getAllWithBalanceFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAccount(name: String, type: AccountType, balance: BigDecimal) {
        viewModelScope.launch {
            accountRepository.insert(AccountEntity(name = name, type = type, initialBalance = balance))
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
                AccountCard(
                    name = account.name,
                    balance = account.balance,
                    type = account.type.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() },
                    colorHex = account.colorHex,
                    onClick = {}
                )
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var type by remember { mutableStateOf(AccountType.BANK) }
        var balance by remember { mutableStateOf("0") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Account") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    OutlinedTextField(name, { name = it }, label = { Text("Account Name") }, modifier = Modifier.fillMaxWidth())
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)) {
                        AccountType.entries.forEach { t ->
                            FilterChip(selected = type == t, onClick = { type = t },
                                label = { Text(t.name.take(4), style = MaterialTheme.typography.labelSmall) })
                        }
                    }
                    OutlinedTextField(balance, { balance = it }, label = { Text("Initial Balance") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    balance.toBigDecimalOrNull()?.let { viewModel.addAccount(name, type, it); showAddDialog = false }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddDialog = false }) { Text("Cancel") } }
        )
    }
}
