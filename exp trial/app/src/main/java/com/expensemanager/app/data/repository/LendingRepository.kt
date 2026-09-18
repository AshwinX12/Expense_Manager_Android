package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.LendingDao
import com.expensemanager.app.data.db.dao.TransactionDao
import com.expensemanager.app.data.db.dao.AccountDao
import com.expensemanager.app.data.db.entity.LendingEntity
import com.expensemanager.app.data.db.entity.LendingStatus
import com.expensemanager.app.data.db.entity.LendingType
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.db.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

class LendingRepository @Inject constructor(
    private val lendingDao: LendingDao,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) {
    suspend fun insert(lending: LendingEntity): Long {
        val id = lendingDao.insert(lending)
        
        // Log transaction
        val defaultAccount = accountDao.getDefault()
        val transaction = TransactionEntity(
            amount = lending.amount,
            type = if (lending.type == LendingType.BORROWED) TransactionType.DEBT_TRANSFER_IN else TransactionType.DEBT_TRANSFER_OUT,
            date = LocalDate.now(),
            note = "${if (lending.type == LendingType.BORROWED) "Borrowed from" else "Lent to"}: ${lending.personName}",
            accountId = defaultAccount?.id
        )
        transactionDao.insert(transaction)
        
        return id
    }
    suspend fun update(lending: LendingEntity) = lendingDao.update(lending)
    suspend fun delete(lending: LendingEntity) = lendingDao.delete(lending)
    suspend fun getById(id: Long) = lendingDao.getById(id)
    fun getAllFlow() = lendingDao.getAllFlow()
    fun getByTypeFlow(type: LendingType) = lendingDao.getByTypeFlow(type)
    fun getByPersonFlow(name: String) = lendingDao.getByPersonFlow(name)
    fun getTotalLentFlow() = lendingDao.getTotalLentFlow()
    fun getTotalBorrowedFlow() = lendingDao.getTotalBorrowedFlow()
    suspend fun getAllPersonNames() = lendingDao.getAllPersonNames()

    suspend fun recordPayment(id: Long, paymentAmount: BigDecimal) {
        val lending = lendingDao.getById(id) ?: return
        val newRepaid = lending.repaidAmount + paymentAmount
        val newStatus = when {
            newRepaid >= lending.amount -> LendingStatus.SETTLED
            newRepaid > BigDecimal.ZERO -> LendingStatus.PARTIALLY_REPAID
            else -> LendingStatus.PENDING
        }
        lendingDao.update(lending.copy(repaidAmount = newRepaid, status = newStatus))

        // Log transaction
        val defaultAccount = accountDao.getDefault()
        val transaction = TransactionEntity(
            amount = paymentAmount,
            type = if (lending.type == LendingType.BORROWED) TransactionType.DEBT_TRANSFER_OUT else TransactionType.DEBT_TRANSFER_IN,
            date = LocalDate.now(),
            note = "Repayment: ${lending.personName}",
            accountId = defaultAccount?.id
        )
        transactionDao.insert(transaction)
    }

    suspend fun markSettled(id: Long) {
        val lending = lendingDao.getById(id) ?: return
        val amountToRepay = lending.amount - lending.repaidAmount
        lendingDao.update(lending.copy(status = LendingStatus.SETTLED, repaidAmount = lending.amount))

        if (amountToRepay > BigDecimal.ZERO) {
            val defaultAccount = accountDao.getDefault()
            val transaction = TransactionEntity(
                amount = amountToRepay,
                type = if (lending.type == LendingType.BORROWED) TransactionType.DEBT_TRANSFER_OUT else TransactionType.DEBT_TRANSFER_IN,
                date = LocalDate.now(),
                note = "Settled: ${lending.personName}",
                accountId = defaultAccount?.id
            )
            transactionDao.insert(transaction)
        }
    }
}
