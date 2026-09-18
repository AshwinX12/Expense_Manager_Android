package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.AccountDao
import com.expensemanager.app.data.db.entity.AccountEntity
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) {
    suspend fun insert(account: AccountEntity) = accountDao.insert(account)
    suspend fun update(account: AccountEntity) = accountDao.update(account)
    suspend fun delete(account: AccountEntity) = accountDao.delete(account)
    suspend fun getById(id: Long) = accountDao.getById(id)
    fun getAllFlow() = accountDao.getAllFlow()
    suspend fun getAll() = accountDao.getAll()
    suspend fun getDefault() = accountDao.getDefault()
    fun getBalanceFlow(accountId: Long) = accountDao.getBalanceFlow(accountId)
    fun getAllWithBalanceFlow() = accountDao.getAllWithBalanceFlow()
}
