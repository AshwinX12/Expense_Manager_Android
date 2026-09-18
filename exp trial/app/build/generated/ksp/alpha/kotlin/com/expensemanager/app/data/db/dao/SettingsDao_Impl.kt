package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.entity.SettingsEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
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
public class SettingsDao_Impl(
  __db: RoomDatabase,
) : SettingsDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSettingsEntity: EntityInsertAdapter<SettingsEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSettingsEntity = object : EntityInsertAdapter<SettingsEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `settings` (`key`,`value`) VALUES (?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SettingsEntity) {
        statement.bindText(1, entity.key)
        statement.bindText(2, entity.value)
      }
    }
  }

  public override suspend fun insert(setting: SettingsEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfSettingsEntity.insert(_connection, setting)
  }

  public override suspend fun getValue(key: String): String? {
    val _sql: String = "SELECT value FROM settings WHERE `key` = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        val _result: String?
        if (_stmt.step()) {
          if (_stmt.isNull(0)) {
            _result = null
          } else {
            _result = _stmt.getText(0)
          }
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getValueFlow(key: String): Flow<String?> {
    val _sql: String = "SELECT value FROM settings WHERE `key` = ?"
    return createFlow(__db, false, arrayOf("settings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        val _result: String?
        if (_stmt.step()) {
          if (_stmt.isNull(0)) {
            _result = null
          } else {
            _result = _stmt.getText(0)
          }
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<SettingsEntity> {
    val _sql: String = "SELECT * FROM settings"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _result: MutableList<SettingsEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SettingsEntity
          val _tmpKey: String
          _tmpKey = _stmt.getText(_columnIndexOfKey)
          val _tmpValue: String
          _tmpValue = _stmt.getText(_columnIndexOfValue)
          _item = SettingsEntity(_tmpKey,_tmpValue)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(key: String) {
    val _sql: String = "DELETE FROM settings WHERE `key` = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
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
