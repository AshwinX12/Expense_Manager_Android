package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.entity.SubcategoryEntity
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
public class SubcategoryDao_Impl(
  __db: RoomDatabase,
) : SubcategoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSubcategoryEntity: EntityInsertAdapter<SubcategoryEntity>

  private val __deleteAdapterOfSubcategoryEntity: EntityDeleteOrUpdateAdapter<SubcategoryEntity>

  private val __updateAdapterOfSubcategoryEntity: EntityDeleteOrUpdateAdapter<SubcategoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSubcategoryEntity = object : EntityInsertAdapter<SubcategoryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `subcategories` (`id`,`name`,`parentCategoryId`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SubcategoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindLong(3, entity.parentCategoryId)
      }
    }
    this.__deleteAdapterOfSubcategoryEntity = object :
        EntityDeleteOrUpdateAdapter<SubcategoryEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `subcategories` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SubcategoryEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfSubcategoryEntity = object :
        EntityDeleteOrUpdateAdapter<SubcategoryEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `subcategories` SET `id` = ?,`name` = ?,`parentCategoryId` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SubcategoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindLong(3, entity.parentCategoryId)
        statement.bindLong(4, entity.id)
      }
    }
  }

  public override suspend fun insert(subcategory: SubcategoryEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfSubcategoryEntity.insertAndReturnId(_connection,
        subcategory)
    _result
  }

  public override suspend fun delete(subcategory: SubcategoryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfSubcategoryEntity.handle(_connection, subcategory)
  }

  public override suspend fun update(subcategory: SubcategoryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfSubcategoryEntity.handle(_connection, subcategory)
  }

  public override fun getByCategoryFlow(categoryId: Long): Flow<List<SubcategoryEntity>> {
    val _sql: String = "SELECT * FROM subcategories WHERE parentCategoryId = ? ORDER BY name"
    return createFlow(__db, false, arrayOf("subcategories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfParentCategoryId: Int = getColumnIndexOrThrow(_stmt, "parentCategoryId")
        val _result: MutableList<SubcategoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubcategoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpParentCategoryId: Long
          _tmpParentCategoryId = _stmt.getLong(_columnIndexOfParentCategoryId)
          _item = SubcategoryEntity(_tmpId,_tmpName,_tmpParentCategoryId)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByCategory(categoryId: Long): List<SubcategoryEntity> {
    val _sql: String = "SELECT * FROM subcategories WHERE parentCategoryId = ? ORDER BY name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfParentCategoryId: Int = getColumnIndexOrThrow(_stmt, "parentCategoryId")
        val _result: MutableList<SubcategoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubcategoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpParentCategoryId: Long
          _tmpParentCategoryId = _stmt.getLong(_columnIndexOfParentCategoryId)
          _item = SubcategoryEntity(_tmpId,_tmpName,_tmpParentCategoryId)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): SubcategoryEntity? {
    val _sql: String = "SELECT * FROM subcategories WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfParentCategoryId: Int = getColumnIndexOrThrow(_stmt, "parentCategoryId")
        val _result: SubcategoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpParentCategoryId: Long
          _tmpParentCategoryId = _stmt.getLong(_columnIndexOfParentCategoryId)
          _result = SubcategoryEntity(_tmpId,_tmpName,_tmpParentCategoryId)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<SubcategoryEntity> {
    val _sql: String = "SELECT * FROM subcategories ORDER BY name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfParentCategoryId: Int = getColumnIndexOrThrow(_stmt, "parentCategoryId")
        val _result: MutableList<SubcategoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubcategoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpParentCategoryId: Long
          _tmpParentCategoryId = _stmt.getLong(_columnIndexOfParentCategoryId)
          _item = SubcategoryEntity(_tmpId,_tmpName,_tmpParentCategoryId)
          _result.add(_item)
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
