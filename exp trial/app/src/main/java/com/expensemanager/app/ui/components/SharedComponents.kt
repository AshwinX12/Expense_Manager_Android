package com.expensemanager.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensemanager.app.ui.theme.*
import java.math.BigDecimal

/**
 * Top app bar colors that match the screen background rather than Material3's default
 * surface tone — keeps title bars flush with the page instead of reading as a separate
 * "boxed" bar, consistent with how Home/Reports render their headers inline.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun flatTopAppBarColors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.background,
    scrolledContainerColor = MaterialTheme.colorScheme.background
)

/**
 * Memphis theme's floating geometric shapes — circles/quarter-circles at screen corners,
 * rendered behind content at low opacity. Per the theme spec these are load-bearing for
 * the theme's identity, not optional decoration, so this is shown only when Memphis is active.
 */
@Composable
fun MemphisBackdrop(modifier: Modifier = Modifier, isDark: Boolean = false) {
    val alpha = if (isDark) 0.18f else 0.35f
    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = com.expensemanager.app.ui.theme.MemphisColors.DecorativeYellow.copy(alpha = alpha),
            radius = size.width * 0.35f,
            center = androidx.compose.ui.geometry.Offset(size.width * 0.9f, size.height * 0.05f)
        )
        drawCircle(
            color = com.expensemanager.app.ui.theme.MemphisColors.DecorativeTeal.copy(alpha = alpha),
            radius = size.width * 0.25f,
            center = androidx.compose.ui.geometry.Offset(-size.width * 0.05f, size.height * 0.4f)
        )
        drawCircle(
            color = com.expensemanager.app.ui.theme.MemphisColors.DecorativeBlue.copy(alpha = alpha),
            radius = size.width * 0.3f,
            center = androidx.compose.ui.geometry.Offset(size.width * 0.85f, size.height * 0.95f)
        )
    }
}

@Composable
fun EmptyStateView(
    icon: @Composable () -> Unit,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.SpacingXxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()
        Spacer(modifier = Modifier.height(Dimens.SpacingLg))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingSm))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (action != null) {
            Spacer(modifier = Modifier.height(Dimens.SpacingLg))
            action()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search…",
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        placeholder = {
            Text(placeholder, style = MaterialTheme.typography.bodyMedium)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        singleLine = true,
        shape = CardShape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun AccountCard(
    name: String,
    balance: BigDecimal,
    type: String,
    colorHex: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.CardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.AvatarSize)
                    .clip(CircleShape)
                    .background(colorHex.toComposeColor().copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = colorHex.toComposeColor()
                )
            }
            Spacer(modifier = Modifier.width(Dimens.SpacingMd))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AmountText(
                amount = balance,
                style = AmountTextStyle.Small
            )
            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(Dimens.SpacingXs))
                trailingContent()
            }
        }
    }
}
