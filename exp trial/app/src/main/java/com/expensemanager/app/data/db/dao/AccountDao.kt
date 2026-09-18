package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.AccountEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity): Long

    @Update
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: Long): AccountEntity?

    @Query("SELECT * FROM accounts ORDER BY sortOrder, name")
    fun getAllFlow(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts ORDER BY sortOrder, name")
    suspend fun getAll(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE isDefault = 1 LIMIT 1")
    suspend fun getDefault(): AccountEntity?

    @Query("""
        SELECT a.initialBalance + 
            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
        FROM accounts a WHERE a.id = :accountId
    """)
    fun getBalanceFlow(accountId: Long): Flow<BigDecimal>

    @Query("""
        SELECT a.id, a.name, a.type, a.currency, a.initialBalance, a.iconName, a.colorHex, a.isDefault, a.sortOrder, a.customTypeName,
            a.initialBalance + 
            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
            as balance
        FROM accounts a ORDER BY a.sortOrder, a.name
    """)
    fun getAllWithBalanceFlow(): Flow<List<AccountWithBalance>>

    @Query("""
        SELECT COALESCE(SUM(
            a.initialBalance +
            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
        ), 0)
        FROM accounts a
    """)
    suspend fun getTotalBalance(): BigDecimal
}

data class AccountWithBalance(
    val id: Long,
    val name: String,
    val type: com.expensemanager.app.data.db.entity.AccountType,
    val currency: String,
    val initialBalance: BigDecimal,
    val iconName: String,
    val colorHex: String,
    val isDefault: Boolean,
    val sortOrder: Int,
    val customTypeName: String? = null,
    val balance: BigDecimal
)
