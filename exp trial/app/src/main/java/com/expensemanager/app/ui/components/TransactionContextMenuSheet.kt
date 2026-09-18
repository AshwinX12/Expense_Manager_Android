package com.expensemanager.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensemanager.app.domain.model.TransactionWithDetails
import com.expensemanager.app.ui.theme.Dimens
import com.expensemanager.app.util.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionContextMenuSheet(
    transaction: TransactionWithDetails,
    onDismiss: () -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit,
    onSchedule: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.SpacingHuge)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingMd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transaction.categoryName ?: "Uncategorized",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${transaction.transaction.date} • ${transaction.accountName ?: ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = transaction.transaction.amount.formatCurrency(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            HorizontalDivider()
            Spacer(modifier = Modifier.height(Dimens.SpacingSm))

            // Actions
            ListItem(
                headlineContent = { Text("Duplicate Transaction") },
                leadingContent = { Icon(Icons.Default.ContentCopy, contentDescription = null) },
                modifier = Modifier.clickable {
                    onDismiss()
                    onDuplicate()
                }
            )
            ListItem(
                headlineContent = { Text("Create Scheduled") },
                leadingContent = { Icon(Icons.Default.Schedule, contentDescription = null) },
                modifier = Modifier.clickable {
                    onDismiss()
                    onSchedule()
                }
            )
            ListItem(
                headlineContent = { Text("Delete Transaction") },
                leadingContent = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                colors = ListItemDefaults.colors(headlineColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.clickable {
                    onDismiss()
                    onDelete()
                }
            )
            ListItem(
                headlineContent = { Text("Cancel") },
                leadingContent = { Icon(Icons.Default.Cancel, contentDescription = null) },
                modifier = Modifier.clickable {
                    onDismiss()
                }
            )
        }
    }
}
