package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.QuickAddShortcutEntity
import java.math.BigDecimal
import javax.`annotation`.processing.Generated
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
public class QuickAddShortcutDao_Impl(
  __db: RoomDatabase,
) : QuickAddShortcutDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfQuickAddShortcutEntity: EntityInsertAdapter<QuickAddShortcutEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfQuickAddShortcutEntity:
      EntityDeleteOrUpdateAdapter<QuickAddShortcutEntity>

  private val __updateAdapterOfQuickAddShortcutEntity:
      EntityDeleteOrUpdateAdapter<QuickAddShortcutEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfQuickAddShortcutEntity = object :
        EntityInsertAdapter<QuickAddShortcutEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `quick_add_shortcuts` (`id`,`label`,`amount`,`categoryId`,`accountId`,`iconName`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: QuickAddShortcutEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.label)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindLong(4, entity.categoryId)
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpAccountId)
        }
        statement.bindText(6, entity.iconName)
        statement.bindLong(7, entity.sortOrder.toLong())
      }
    }
    this.__deleteAdapterOfQuickAddShortcutEntity = object :
        EntityDeleteOrUpdateAdapter<QuickAddShortcutEntity>() {
      protected override fun createQuery(): String =
          "DELETE FROM `quick_add_shortcuts` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: QuickAddShortcutEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfQuickAddShortcutEntity = object :
        EntityDeleteOrUpdateAdapter<QuickAddShortcutEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `quick_add_shortcuts` SET `id` = ?,`label` = ?,`amount` = ?,`categoryId` = ?,`accountId` = ?,`iconName` = ?,`sortOrder` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: QuickAddShortcutEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.label)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindLong(4, entity.categoryId)
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpAccountId)
        }
        statement.bindText(6, entity.iconName)
        statement.bindLong(7, entity.sortOrder.toLong())
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun insert(shortcut: QuickAddShortcutEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfQuickAddShortcutEntity.insertAndReturnId(_connection,
        shortcut)
    _result
  }

  public override suspend fun delete(shortcut: QuickAddShortcutEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfQuickAddShortcutEntity.handle(_connection, shortcut)
  }

  public override suspend fun update(shortcut: QuickAddShortcutEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfQuickAddShortcutEntity.handle(_connection, shortcut)
  }

  public override fun getAllFlow(): Flow<List<QuickAddShortcutEntity>> {
    val _sql: String = "SELECT * FROM quick_add_shortcuts ORDER BY sortOrder"
    return createFlow(__db, false, arrayOf("quick_add_shortcuts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<QuickAddShortcutEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuickAddShortcutEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_1
          }
          val _tmpCategoryId: Long
          _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              QuickAddShortcutEntity(_tmpId,_tmpLabel,_tmpAmount,_tmpCategoryId,_tmpAccountId,_tmpIconName,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<QuickAddShortcutEntity> {
    val _sql: String = "SELECT * FROM quick_add_shortcuts ORDER BY sortOrder"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<QuickAddShortcutEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuickAddShortcutEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_1
          }
          val _tmpCategoryId: Long
          _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              QuickAddShortcutEntity(_tmpId,_tmpLabel,_tmpAmount,_tmpCategoryId,_tmpAccountId,_tmpIconName,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): QuickAddShortcutEntity? {
    val _sql: String = "SELECT * FROM quick_add_shortcuts WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: QuickAddShortcutEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_1
          }
          val _tmpCategoryId: Long
          _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _result =
              QuickAddShortcutEntity(_tmpId,_tmpLabel,_tmpAmount,_tmpCategoryId,_tmpAccountId,_tmpIconName,_tmpSortOrder)
        } else {
          _result = null
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
