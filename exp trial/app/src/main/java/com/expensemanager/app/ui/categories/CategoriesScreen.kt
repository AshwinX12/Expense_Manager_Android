package com.expensemanager.app.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.BudgetEntity
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.data.db.entity.SubcategoryEntity
import com.expensemanager.app.data.repository.BudgetRepository
import com.expensemanager.app.data.repository.CategoryRepository
import com.expensemanager.app.ui.components.CreateCategorySheet
import com.expensemanager.app.ui.components.flatTopAppBarColors
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.capitalizeFirst
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    val categories = categoryRepository.getAllCategoriesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val budgets = budgetRepository.getAllFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCategory(name: String, color: String) {
        viewModelScope.launch {
            categoryRepository.insertCategory(
                CategoryEntity(name = name.capitalizeFirst(), colorHex = color)
            )
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            budgetRepository.removeCategory(category.id)
            categoryRepository.deleteCategory(category)
        }
    }

    fun addSubcategory(name: String, parentId: Long) {
        viewModelScope.launch {
            categoryRepository.insertSubcategory(SubcategoryEntity(name = name, parentCategoryId = parentId))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onNavigateBack: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val budgets by viewModel.budgets.collectAsStateWithLifecycle()
    var pendingDelete by remember { mutableStateOf<CategoryEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showSubcategoryDialog by remember { mutableStateOf<CategoryEntity?>(null) }
    val themeStyle = LocalThemeStyle.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories", fontWeight = FontWeight.SemiBold) },
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
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
        ) {
            items(categories, key = { it.id }) { category ->
                Card(
                    shape = CardShape,
                    elevation = CardDefaults.cardElevation(Dimens.CardElevation)
                ) {
                    ListItem(
                        headlineContent = { Text(category.name, fontWeight = FontWeight.Medium) },
                        leadingContent = {
                            Box(
                                Modifier.size(32.dp).clip(CircleShape)
                                    .background(category.colorHex.toComposeColor()),
                                Alignment.Center
                            ) {
                                Text(
                                    category.name.take(1).uppercase(),
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        trailingContent = {
                            Row {
                                IconButton(onClick = { showSubcategoryDialog = category }) {
                                    Icon(Icons.Default.SubdirectoryArrowRight, "Add subcategory", Modifier.size(18.dp))
                                }
                                if (!category.isDefault) {
                                    IconButton(onClick = {
                                        // Only interrupt with a confirmation when budgets are affected.
                                        val affected = budgets.any { category.id in it.effectiveCategoryIds }
                                        if (affected) pendingDelete = category else viewModel.deleteCategory(category)
                                    }) {
                                        Icon(Icons.Default.Delete, "Delete", Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    pendingDelete?.let { category ->
        // Budgets that only tracked this category go with it; clubbed budgets survive and
        // just stop tracking it (see BudgetRepository.removeCategory).
        val deletedBudgets = budgets.filter { it.effectiveCategoryIds == listOf(category.id) }
        val shrunkBudgets = budgets.filter { category.id in it.effectiveCategoryIds && it.effectiveCategoryIds.size > 1 }
        fun label(b: BudgetEntity) = b.name ?: b.effectiveCategoryIds.joinToString(" + ") { id ->
            categories.find { it.id == id }?.name ?: "Unknown"
        }.ifEmpty { "Overall" }

        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            icon = { Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Delete ${category.name}?") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    if (deletedBudgets.isNotEmpty()) {
                        Text("These budgets will be permanently deleted:", fontWeight = FontWeight.SemiBold)
                        deletedBudgets.forEach { Text("• ${label(it)}", style = MaterialTheme.typography.bodyMedium) }
                    }
                    if (shrunkBudgets.isNotEmpty()) {
                        Text("These combined budgets will keep going without ${category.name}:", fontWeight = FontWeight.SemiBold)
                        shrunkBudgets.forEach { Text("• ${label(it)}", style = MaterialTheme.typography.bodyMedium) }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.deleteCategory(category); pendingDelete = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Delete") }
            },
            dismissButton = { TextButton(onClick = { pendingDelete = null }) { Text("Cancel") } }
        )
    }

    if (showAddDialog) {
        CreateCategorySheet(
            onDismiss = { showAddDialog = false },
            onSave = { name, color ->
                viewModel.addCategory(name, color)
                showAddDialog = false
            },
            palette = categoryPaletteFor(themeStyle),
            usedColors = categories.map { it.colorHex }
        )
    }

    showSubcategoryDialog?.let { category ->
        var name by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showSubcategoryDialog = null },
            title = { Text("Add Subcategory to ${category.name}") },
            text = {
                OutlinedTextField(name, { name = it }, label = { Text("Subcategory Name") }, modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                Button(onClick = { if (name.isNotBlank()) { viewModel.addSubcategory(name, category.id); showSubcategoryDialog = null } }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showSubcategoryDialog = null }) { Text("Cancel") } }
        )
    }
}
