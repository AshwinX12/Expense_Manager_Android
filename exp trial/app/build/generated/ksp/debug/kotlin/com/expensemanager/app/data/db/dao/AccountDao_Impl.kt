package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.AccountEntity
import com.expensemanager.app.`data`.db.entity.AccountType
import java.math.BigDecimal
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AccountDao_Impl(
  __db: RoomDatabase,
) : AccountDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAccountEntity: EntityInsertAdapter<AccountEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfAccountEntity: EntityDeleteOrUpdateAdapter<AccountEntity>

  private val __updateAdapterOfAccountEntity: EntityDeleteOrUpdateAdapter<AccountEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAccountEntity = object : EntityInsertAdapter<AccountEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `accounts` (`id`,`name`,`type`,`currency`,`initialBalance`,`iconName`,`colorHex`,`isDefault`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AccountEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmp: String? = __converters.fromAccountType(entity.type)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindText(4, entity.currency)
        val _tmp_1: String? = __converters.fromBigDecimal(entity.initialBalance)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_1)
        }
        statement.bindText(6, entity.iconName)
        statement.bindText(7, entity.colorHex)
        val _tmp_2: Int = if (entity.isDefault) 1 else 0
        statement.bindLong(8, _tmp_2.toLong())
        statement.bindLong(9, entity.sortOrder.toLong())
      }
    }
    this.__deleteAdapterOfAccountEntity = object : EntityDeleteOrUpdateAdapter<AccountEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `accounts` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AccountEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfAccountEntity = object : EntityDeleteOrUpdateAdapter<AccountEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `accounts` SET `id` = ?,`name` = ?,`type` = ?,`currency` = ?,`initialBalance` = ?,`iconName` = ?,`colorHex` = ?,`isDefault` = ?,`sortOrder` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AccountEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmp: String? = __converters.fromAccountType(entity.type)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindText(4, entity.currency)
        val _tmp_1: String? = __converters.fromBigDecimal(entity.initialBalance)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_1)
        }
        statement.bindText(6, entity.iconName)
        statement.bindText(7, entity.colorHex)
        val _tmp_2: Int = if (entity.isDefault) 1 else 0
        statement.bindLong(8, _tmp_2.toLong())
        statement.bindLong(9, entity.sortOrder.toLong())
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun insert(account: AccountEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfAccountEntity.insertAndReturnId(_connection, account)
    _result
  }

  public override suspend fun delete(account: AccountEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfAccountEntity.handle(_connection, account)
  }

  public override suspend fun update(account: AccountEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfAccountEntity.handle(_connection, account)
  }

  public override suspend fun getById(id: Long): AccountEntity? {
    val _sql: String = "SELECT * FROM accounts WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfInitialBalance: Int = getColumnIndexOrThrow(_stmt, "initialBalance")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: AccountEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpType: AccountType
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_1: AccountType? = __converters.toAccountType(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.AccountType', but it was NULL.")
          } else {
            _tmpType = _tmp_1
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpInitialBalance: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfInitialBalance)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfInitialBalance)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpInitialBalance = _tmp_3
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp_4 != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _result =
              AccountEntity(_tmpId,_tmpName,_tmpType,_tmpCurrency,_tmpInitialBalance,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<AccountEntity>> {
    val _sql: String = "SELECT * FROM accounts ORDER BY sortOrder, name"
    return createFlow(__db, false, arrayOf("accounts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfInitialBalance: Int = getColumnIndexOrThrow(_stmt, "initialBalance")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<AccountEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AccountEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpType: AccountType
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_1: AccountType? = __converters.toAccountType(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.AccountType', but it was NULL.")
          } else {
            _tmpType = _tmp_1
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpInitialBalance: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfInitialBalance)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfInitialBalance)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpInitialBalance = _tmp_3
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp_4 != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              AccountEntity(_tmpId,_tmpName,_tmpType,_tmpCurrency,_tmpInitialBalance,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<AccountEntity> {
    val _sql: String = "SELECT * FROM accounts ORDER BY sortOrder, name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfInitialBalance: Int = getColumnIndexOrThrow(_stmt, "initialBalance")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<AccountEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AccountEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpType: AccountType
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_1: AccountType? = __converters.toAccountType(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.AccountType', but it was NULL.")
          } else {
            _tmpType = _tmp_1
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpInitialBalance: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfInitialBalance)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfInitialBalance)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpInitialBalance = _tmp_3
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp_4 != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              AccountEntity(_tmpId,_tmpName,_tmpType,_tmpCurrency,_tmpInitialBalance,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDefault(): AccountEntity? {
    val _sql: String = "SELECT * FROM accounts WHERE isDefault = 1 LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfInitialBalance: Int = getColumnIndexOrThrow(_stmt, "initialBalance")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: AccountEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpType: AccountType
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_1: AccountType? = __converters.toAccountType(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.AccountType', but it was NULL.")
          } else {
            _tmpType = _tmp_1
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpInitialBalance: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfInitialBalance)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfInitialBalance)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpInitialBalance = _tmp_3
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp_4 != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _result =
              AccountEntity(_tmpId,_tmpName,_tmpType,_tmpCurrency,_tmpInitialBalance,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getBalanceFlow(accountId: Long): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT a.initialBalance + 
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
        |        FROM accounts a WHERE a.id = ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions", "accounts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, accountId)
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp: String?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(0)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_1
          }
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type <java.math.BigDecimal>.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllWithBalanceFlow(): Flow<List<AccountWithBalance>> {
    val _sql: String = """
        |
        |        SELECT a.id, a.name, a.type, a.currency, a.initialBalance, a.iconName, a.colorHex, a.isDefault, a.sortOrder,
        |            a.initialBalance + 
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
        |            as balance
        |        FROM accounts a ORDER BY a.sortOrder, a.name
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions", "accounts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = 0
        val _columnIndexOfName: Int = 1
        val _columnIndexOfType: Int = 2
        val _columnIndexOfCurrency: Int = 3
        val _columnIndexOfInitialBalance: Int = 4
        val _columnIndexOfIconName: Int = 5
        val _columnIndexOfColorHex: Int = 6
        val _columnIndexOfIsDefault: Int = 7
        val _columnIndexOfSortOrder: Int = 8
        val _columnIndexOfBalance: Int = 9
        val _result: MutableList<AccountWithBalance> = mutableListOf()
        while (_stmt.step()) {
          val _item: AccountWithBalance
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpType: AccountType
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_1: AccountType? = __converters.toAccountType(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.AccountType', but it was NULL.")
          } else {
            _tmpType = _tmp_1
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpInitialBalance: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfInitialBalance)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfInitialBalance)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpInitialBalance = _tmp_3
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp_4 != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpBalance: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfBalance)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfBalance)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpBalance = _tmp_6
          }
          _item =
              AccountWithBalance(_tmpId,_tmpName,_tmpType,_tmpCurrency,_tmpInitialBalance,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder,_tmpBalance)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalBalance(): BigDecimal {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(
        |            a.initialBalance +
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'TRANSFER_TO_GOAL' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) +
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_IN' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0) -
        |            COALESCE((SELECT SUM(CASE WHEN t.type = 'DEBT_TRANSFER_OUT' THEN t.amount ELSE 0 END) FROM transactions t WHERE t.accountId = a.id), 0)
        |        ), 0)
        |        FROM accounts a
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp: String?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(0)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_1
          }
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type <java.math.BigDecimal>.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
