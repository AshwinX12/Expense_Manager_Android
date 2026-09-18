package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.SplitExpenseEntity
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
public class SplitExpenseDao_Impl(
  __db: RoomDatabase,
) : SplitExpenseDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSplitExpenseEntity: EntityInsertAdapter<SplitExpenseEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfSplitExpenseEntity: EntityDeleteOrUpdateAdapter<SplitExpenseEntity>

  private val __updateAdapterOfSplitExpenseEntity: EntityDeleteOrUpdateAdapter<SplitExpenseEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSplitExpenseEntity = object : EntityInsertAdapter<SplitExpenseEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `split_expenses` (`id`,`transactionId`,`personName`,`shareAmount`,`isSettled`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SplitExpenseEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.transactionId)
        statement.bindText(3, entity.personName)
        val _tmp: String? = __converters.fromBigDecimal(entity.shareAmount)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        val _tmp_1: Int = if (entity.isSettled) 1 else 0
        statement.bindLong(5, _tmp_1.toLong())
      }
    }
    this.__deleteAdapterOfSplitExpenseEntity = object :
        EntityDeleteOrUpdateAdapter<SplitExpenseEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `split_expenses` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SplitExpenseEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfSplitExpenseEntity = object :
        EntityDeleteOrUpdateAdapter<SplitExpenseEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `split_expenses` SET `id` = ?,`transactionId` = ?,`personName` = ?,`shareAmount` = ?,`isSettled` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SplitExpenseEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.transactionId)
        statement.bindText(3, entity.personName)
        val _tmp: String? = __converters.fromBigDecimal(entity.shareAmount)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        val _tmp_1: Int = if (entity.isSettled) 1 else 0
        statement.bindLong(5, _tmp_1.toLong())
        statement.bindLong(6, entity.id)
      }
    }
  }

  public override suspend fun insert(split: SplitExpenseEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfSplitExpenseEntity.insertAndReturnId(_connection, split)
    _result
  }

  public override suspend fun insertAll(splits: List<SplitExpenseEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSplitExpenseEntity.insert(_connection, splits)
  }

  public override suspend fun delete(split: SplitExpenseEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfSplitExpenseEntity.handle(_connection, split)
  }

  public override suspend fun update(split: SplitExpenseEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfSplitExpenseEntity.handle(_connection, split)
  }

  public override fun getByTransactionFlow(transactionId: Long): Flow<List<SplitExpenseEntity>> {
    val _sql: String = "SELECT * FROM split_expenses WHERE transactionId = ?"
    return createFlow(__db, false, arrayOf("split_expenses")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfShareAmount: Int = getColumnIndexOrThrow(_stmt, "shareAmount")
        val _columnIndexOfIsSettled: Int = getColumnIndexOrThrow(_stmt, "isSettled")
        val _result: MutableList<SplitExpenseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SplitExpenseEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
          val _tmpShareAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfShareAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfShareAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpShareAmount = _tmp_1
          }
          val _tmpIsSettled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsSettled).toInt()
          _tmpIsSettled = _tmp_2 != 0
          _item =
              SplitExpenseEntity(_tmpId,_tmpTransactionId,_tmpPersonName,_tmpShareAmount,_tmpIsSettled)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByTransaction(transactionId: Long): List<SplitExpenseEntity> {
    val _sql: String = "SELECT * FROM split_expenses WHERE transactionId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfShareAmount: Int = getColumnIndexOrThrow(_stmt, "shareAmount")
        val _columnIndexOfIsSettled: Int = getColumnIndexOrThrow(_stmt, "isSettled")
        val _result: MutableList<SplitExpenseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SplitExpenseEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
          val _tmpShareAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfShareAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfShareAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpShareAmount = _tmp_1
          }
          val _tmpIsSettled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsSettled).toInt()
          _tmpIsSettled = _tmp_2 != 0
          _item =
              SplitExpenseEntity(_tmpId,_tmpTransactionId,_tmpPersonName,_tmpShareAmount,_tmpIsSettled)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteByTransaction(transactionId: Long) {
    val _sql: String = "DELETE FROM split_expenses WHERE transactionId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setSettled(id: Long, settled: Boolean) {
    val _sql: String = "UPDATE split_expenses SET isSettled = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (settled) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
