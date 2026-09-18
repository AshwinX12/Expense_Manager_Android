package com.expensemanager.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.formatCurrency
import java.math.BigDecimal

@Composable
fun AmountText(
    amount: BigDecimal,
    currencySymbol: String = com.expensemanager.app.util.AppCurrency.symbol,
    type: TransactionType? = null,
    modifier: Modifier = Modifier,
    style: AmountTextStyle = AmountTextStyle.Large
) {
    val color by animateColorAsState(
        targetValue = when (type) {
            TransactionType.INCOME, TransactionType.DEBT_TRANSFER_IN -> IncomeGreen
            TransactionType.EXPENSE, TransactionType.DEBT_TRANSFER_OUT -> ExpenseRed
            TransactionType.TRANSFER_TO_GOAL -> MaterialTheme.colorScheme.primary
            null -> MaterialTheme.colorScheme.onSurface
        },
        label = "amountColor"
    )

    val prefix = when (type) {
        TransactionType.INCOME, TransactionType.DEBT_TRANSFER_IN -> "+"
        TransactionType.EXPENSE, TransactionType.DEBT_TRANSFER_OUT -> "-"
        else -> ""
    }

    Text(
        text = "$prefix${amount.formatCurrency(currencySymbol)}",
        color = color,
        style = when (style) {
            AmountTextStyle.Large -> MaterialTheme.typography.displayMedium
            AmountTextStyle.Medium -> MaterialTheme.typography.headlineMedium
            AmountTextStyle.Small -> MaterialTheme.typography.titleMedium
            AmountTextStyle.Compact -> MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        },
        modifier = modifier
    )
}

enum class AmountTextStyle { Large, Medium, Small, Compact }

@Composable
fun BudgetProgressBar(
    spent: BigDecimal,
    budget: BigDecimal,
    percentage: Int,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    height: Int = 8
) {
    val progressColor by animateColorAsState(
        targetValue = when {
            percentage > 100 -> BudgetDanger
            percentage >= 80 -> BudgetWarning
            else -> BudgetGood
        },
        label = "budgetColor"
    )

    val progress = (percentage / 100f).coerceIn(0f, 1.2f)

    Column(modifier = modifier) {
        if (showLabel) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = spent.formatCurrency(),
                    style = MaterialTheme.typography.bodySmall,
                    color = progressColor,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = budget.formatCurrency(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .clip(RoundedCornerShape(height.dp / 2))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progress.coerceAtMost(1f))
                    .clip(RoundedCornerShape(height.dp / 2))
                    .background(progressColor)
            )
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.labelSmall,
                color = progressColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
