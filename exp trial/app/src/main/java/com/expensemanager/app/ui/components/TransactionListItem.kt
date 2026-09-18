package com.expensemanager.app.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.domain.model.TransactionWithDetails
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.displayLabel

import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionGroupCard(
    date: LocalDate,
    transactions: List<TransactionWithDetails>,
    onTransactionClick: (Long) -> Unit,
    onTransactionLongClick: (TransactionWithDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Group Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.CardPadding, vertical = Dimens.SpacingMd),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date.displayLabel(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                val totalExpense = transactions.filter { it.transaction.type == TransactionType.EXPENSE }.sumOf { it.transaction.amount }
                val totalIncome = transactions.filter { it.transaction.type == TransactionType.INCOME }.sumOf { it.transaction.amount }
                val net = totalIncome - totalExpense
                
                AmountText(
                    amount = net.abs(),
                    type = if (net < BigDecimal.ZERO) TransactionType.EXPENSE else TransactionType.INCOME,
                    style = AmountTextStyle.Compact
                )
            }
            
            // Transactions list without dividers as per screenshot
            transactions.forEach { txDetail ->
                TransactionListItem(
                    transaction = txDetail,
                    onClick = { onTransactionClick(txDetail.transaction.id) },
                    onLongClick = { onTransactionLongClick(txDetail) },
                    isGrouped = true,
                    modifier = Modifier.padding(horizontal = Dimens.CardPadding, vertical = Dimens.SpacingSm)
                )
            }
            Spacer(modifier = Modifier.height(Dimens.SpacingSm))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionListItem(
    transaction: TransactionWithDetails,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    isGrouped: Boolean = false,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(75),
        label = "pressScale"
    )

    val containerModifier = modifier
        .fillMaxWidth()
        .scale(scale)
        .combinedClickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
            onLongClick = onLongClick
        )

    val content = @Composable {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isGrouped) 0.dp else Dimens.CardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category icon circle
            Box(
                modifier = Modifier
                    .size(Dimens.AvatarSize)
                    .clip(CircleShape)
                    .background(
                        transaction.categoryColor?.toComposeColor()?.copy(alpha = 0.15f)
                            ?: MaterialTheme.colorScheme.primaryContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                val iconChar = if (transaction.transaction.type == TransactionType.TRANSFER_TO_GOAL) "G" 
                               else if (transaction.categoryName == null && !transaction.transaction.note.isNullOrBlank()) transaction.transaction.note.take(1).uppercase()
                               else (transaction.categoryName?.take(1)?.uppercase() ?: "?")
                Text(
                    text = iconChar,
                    style = MaterialTheme.typography.titleMedium,
                    color = transaction.categoryColor?.toComposeColor()
                        ?: MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(Dimens.SpacingMd))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val title = if (transaction.transaction.type == TransactionType.TRANSFER_TO_GOAL) {
                        transaction.transaction.note ?: "Goal Transfer"
                    } else if (transaction.categoryName == null && !transaction.transaction.note.isNullOrBlank()) {
                        transaction.transaction.note
                    } else {
                        transaction.categoryName ?: "Uncategorized"
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (transaction.attachmentCount > 0) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "Has attachments",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (!transaction.transaction.note.isNullOrBlank() && 
                    transaction.transaction.type != TransactionType.TRANSFER_TO_GOAL && 
                    transaction.categoryName != null) {
                    Text(
                        text = transaction.transaction.note,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = transaction.accountName ?: "Unknown Account",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(Dimens.SpacingSm))

            // Amount and Time
            Column(horizontalAlignment = Alignment.End) {
                AmountText(
                    amount = transaction.transaction.amount,
                    type = transaction.transaction.type,
                    style = AmountTextStyle.Compact
                )
                
                val timeString = transaction.transaction.time?.format(DateTimeFormatter.ofPattern("hh:mm a"))
                if (timeString != null) {
                    Text(
                        text = timeString,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    if (isGrouped) {
        Box(modifier = containerModifier) {
            content()
        }
    } else {
        Card(
            modifier = containerModifier,
            shape = CardShape,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
        ) {
            content()
        }
    }
}
