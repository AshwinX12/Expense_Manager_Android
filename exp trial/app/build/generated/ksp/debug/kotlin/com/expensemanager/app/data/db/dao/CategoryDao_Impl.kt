package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.entity.CategoryEntity
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
public class CategoryDao_Impl(
  __db: RoomDatabase,
) : CategoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCategoryEntity: EntityInsertAdapter<CategoryEntity>

  private val __deleteAdapterOfCategoryEntity: EntityDeleteOrUpdateAdapter<CategoryEntity>

  private val __updateAdapterOfCategoryEntity: EntityDeleteOrUpdateAdapter<CategoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfCategoryEntity = object : EntityInsertAdapter<CategoryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `categories` (`id`,`name`,`iconName`,`colorHex`,`isDefault`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CategoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.iconName)
        statement.bindText(4, entity.colorHex)
        val _tmp: Int = if (entity.isDefault) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.sortOrder.toLong())
      }
    }
    this.__deleteAdapterOfCategoryEntity = object : EntityDeleteOrUpdateAdapter<CategoryEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `categories` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CategoryEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfCategoryEntity = object : EntityDeleteOrUpdateAdapter<CategoryEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `categories` SET `id` = ?,`name` = ?,`iconName` = ?,`colorHex` = ?,`isDefault` = ?,`sortOrder` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CategoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.iconName)
        statement.bindText(4, entity.colorHex)
        val _tmp: Int = if (entity.isDefault) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.sortOrder.toLong())
        statement.bindLong(7, entity.id)
      }
    }
  }

  public override suspend fun insert(category: CategoryEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfCategoryEntity.insertAndReturnId(_connection, category)
    _result
  }

  public override suspend fun delete(category: CategoryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfCategoryEntity.handle(_connection, category)
  }

  public override suspend fun update(category: CategoryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfCategoryEntity.handle(_connection, category)
  }

  public override suspend fun getById(id: Long): CategoryEntity? {
    val _sql: String = "SELECT * FROM categories WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: CategoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _result =
              CategoryEntity(_tmpId,_tmpName,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<CategoryEntity>> {
    val _sql: String = "SELECT * FROM categories ORDER BY name ASC"
    return createFlow(__db, false, arrayOf("categories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<CategoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CategoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              CategoryEntity(_tmpId,_tmpName,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<CategoryEntity> {
    val _sql: String = "SELECT * FROM categories ORDER BY name ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsDefault: Int = getColumnIndexOrThrow(_stmt, "isDefault")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _result: MutableList<CategoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CategoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsDefault: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDefault).toInt()
          _tmpIsDefault = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          _item =
              CategoryEntity(_tmpId,_tmpName,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpSortOrder)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTransactionCount(categoryId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM transactions WHERE categoryId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM categories WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
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
