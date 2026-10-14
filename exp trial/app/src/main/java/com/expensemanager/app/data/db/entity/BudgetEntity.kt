package com.expensemanager.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

// No foreign key on categoryId: a cascade there would delete a whole clubbed budget when its first
// category is deleted. BudgetRepository.removeCategory() handles category deletion instead.
@Entity(
    tableName = "budgets",
    indices = [Index("categoryId")]
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // First (or only) category; null = overall budget. Kept so single-category budgets and the
    // category queries behave exactly as before.
    val categoryId: Long? = null,
    val amount: BigDecimal,
    val period: BudgetPeriod,
    val rolloverEnabled: Boolean = false,
    val rolloverAmount: BigDecimal = BigDecimal.ZERO,
    val startDate: LocalDate,
    val alertThreshold: Int = 80, // percentage
    // Every category a clubbed budget covers (includes categoryId). Empty for single-category
    // and overall budgets, so budgets created before this column existed are unaffected.
    @ColumnInfo(defaultValue = "''")
    val categoryIds: List<Long> = emptyList(),
    // Optional custom label, mainly for clubbed budgets.
    val name: String? = null
) {
    /** Categories this budget tracks; empty means overall (all spending). */
    val effectiveCategoryIds: List<Long>
        get() = categoryIds.ifEmpty { listOfNotNull(categoryId) }

    val isClubbed: Boolean get() = effectiveCategoryIds.size > 1
}
