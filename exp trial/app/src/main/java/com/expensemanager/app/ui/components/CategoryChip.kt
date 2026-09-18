package com.expensemanager.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.ui.theme.*

@Composable
fun CategoryChipRow(
    categories: List<CategoryEntity>,
    selectedId: Long?,
    onSelect: (CategoryEntity) -> Unit,
    modifier: Modifier = Modifier,
    onCreateNew: (() -> Unit)? = null
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
        contentPadding = PaddingValues(horizontal = Dimens.ScreenPadding)
    ) {
        items(categories, key = { it.id }) { category ->
            CategoryChip(
                category = category,
                isSelected = category.id == selectedId,
                onClick = { onSelect(category) }
            )
        }
        // "+ New" chip appended after existing categories
        if (onCreateNew != null) {
            item(key = "create_new") {
                CreateNewCategoryChip(onClick = onCreateNew)
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: CategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipColor = category.colorHex.toComposeColor()
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.08f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "chipScale"
    )

    Column(
        modifier = modifier
            .width(64.dp)
            .scale(scale)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.CategoryChipHeight)
                .clip(CircleShape)
                .then(
                    if (isSelected) {
                        Modifier
                            .background(chipColor.copy(alpha = 0.2f))
                            .border(2.dp, chipColor, CircleShape)
                    } else {
                        Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name.take(2).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) chipColor else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isSelected) chipColor else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CreateNewCategoryChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(64.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.CategoryChipHeight)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    1.5.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create new category",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "+ New",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )
    }
}
