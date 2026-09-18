package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.entity.SavedFilterEntity
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
public class SavedFilterDao_Impl(
  __db: RoomDatabase,
) : SavedFilterDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSavedFilterEntity: EntityInsertAdapter<SavedFilterEntity>

  private val __deleteAdapterOfSavedFilterEntity: EntityDeleteOrUpdateAdapter<SavedFilterEntity>

  private val __updateAdapterOfSavedFilterEntity: EntityDeleteOrUpdateAdapter<SavedFilterEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSavedFilterEntity = object : EntityInsertAdapter<SavedFilterEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `saved_filters` (`id`,`name`,`filterJson`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SavedFilterEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.filterJson)
      }
    }
    this.__deleteAdapterOfSavedFilterEntity = object :
        EntityDeleteOrUpdateAdapter<SavedFilterEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `saved_filters` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SavedFilterEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfSavedFilterEntity = object :
        EntityDeleteOrUpdateAdapter<SavedFilterEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `saved_filters` SET `id` = ?,`name` = ?,`filterJson` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SavedFilterEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.filterJson)
        statement.bindLong(4, entity.id)
      }
    }
  }

  public override suspend fun insert(filter: SavedFilterEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfSavedFilterEntity.insertAndReturnId(_connection, filter)
    _result
  }

  public override suspend fun delete(filter: SavedFilterEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfSavedFilterEntity.handle(_connection, filter)
  }

  public override suspend fun update(filter: SavedFilterEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfSavedFilterEntity.handle(_connection, filter)
  }

  public override fun getAllFlow(): Flow<List<SavedFilterEntity>> {
    val _sql: String = "SELECT * FROM saved_filters ORDER BY name"
    return createFlow(__db, false, arrayOf("saved_filters")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfFilterJson: Int = getColumnIndexOrThrow(_stmt, "filterJson")
        val _result: MutableList<SavedFilterEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SavedFilterEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpFilterJson: String
          _tmpFilterJson = _stmt.getText(_columnIndexOfFilterJson)
          _item = SavedFilterEntity(_tmpId,_tmpName,_tmpFilterJson)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): SavedFilterEntity? {
    val _sql: String = "SELECT * FROM saved_filters WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfFilterJson: Int = getColumnIndexOrThrow(_stmt, "filterJson")
        val _result: SavedFilterEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpFilterJson: String
          _tmpFilterJson = _stmt.getText(_columnIndexOfFilterJson)
          _result = SavedFilterEntity(_tmpId,_tmpName,_tmpFilterJson)
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
