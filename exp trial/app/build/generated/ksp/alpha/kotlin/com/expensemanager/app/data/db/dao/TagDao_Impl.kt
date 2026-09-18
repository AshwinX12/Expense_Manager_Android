package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.entity.TagEntity
import com.expensemanager.app.`data`.db.entity.TransactionTagCrossRef
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
public class TagDao_Impl(
  __db: RoomDatabase,
) : TagDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTagEntity: EntityInsertAdapter<TagEntity>

  private val __insertAdapterOfTransactionTagCrossRef: EntityInsertAdapter<TransactionTagCrossRef>

  private val __deleteAdapterOfTagEntity: EntityDeleteOrUpdateAdapter<TagEntity>

  private val __deleteAdapterOfTransactionTagCrossRef:
      EntityDeleteOrUpdateAdapter<TransactionTagCrossRef>

  private val __updateAdapterOfTagEntity: EntityDeleteOrUpdateAdapter<TagEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTagEntity = object : EntityInsertAdapter<TagEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tags` (`id`,`name`,`colorHex`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.colorHex)
      }
    }
    this.__insertAdapterOfTransactionTagCrossRef = object :
        EntityInsertAdapter<TransactionTagCrossRef>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `transaction_tag_cross_ref` (`transactionId`,`tagId`) VALUES (?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionTagCrossRef) {
        statement.bindLong(1, entity.transactionId)
        statement.bindLong(2, entity.tagId)
      }
    }
    this.__deleteAdapterOfTagEntity = object : EntityDeleteOrUpdateAdapter<TagEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `tags` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__deleteAdapterOfTransactionTagCrossRef = object :
        EntityDeleteOrUpdateAdapter<TransactionTagCrossRef>() {
      protected override fun createQuery(): String =
          "DELETE FROM `transaction_tag_cross_ref` WHERE `transactionId` = ? AND `tagId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionTagCrossRef) {
        statement.bindLong(1, entity.transactionId)
        statement.bindLong(2, entity.tagId)
      }
    }
    this.__updateAdapterOfTagEntity = object : EntityDeleteOrUpdateAdapter<TagEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tags` SET `id` = ?,`name` = ?,`colorHex` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.colorHex)
        statement.bindLong(4, entity.id)
      }
    }
  }

  public override suspend fun insert(tag: TagEntity): Long = performSuspending(__db, false, true) {
      _connection ->
    val _result: Long = __insertAdapterOfTagEntity.insertAndReturnId(_connection, tag)
    _result
  }

  public override suspend fun insertCrossRef(crossRef: TransactionTagCrossRef): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfTransactionTagCrossRef.insert(_connection, crossRef)
  }

  public override suspend fun delete(tag: TagEntity): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfTagEntity.handle(_connection, tag)
  }

  public override suspend fun deleteCrossRef(crossRef: TransactionTagCrossRef): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfTransactionTagCrossRef.handle(_connection, crossRef)
  }

  public override suspend fun update(tag: TagEntity): Unit = performSuspending(__db, false, true) {
      _connection ->
    __updateAdapterOfTagEntity.handle(_connection, tag)
  }

  public override fun getAllFlow(): Flow<List<TagEntity>> {
    val _sql: String = "SELECT * FROM tags ORDER BY name"
    return createFlow(__db, false, arrayOf("tags")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          _item = TagEntity(_tmpId,_tmpName,_tmpColorHex)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<TagEntity> {
    val _sql: String = "SELECT * FROM tags ORDER BY name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          _item = TagEntity(_tmpId,_tmpName,_tmpColorHex)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): TagEntity? {
    val _sql: String = "SELECT * FROM tags WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _result: TagEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          _result = TagEntity(_tmpId,_tmpName,_tmpColorHex)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTagsForTransaction(transactionId: Long): List<TagEntity> {
    val _sql: String = """
        |
        |        SELECT t.* FROM tags t 
        |        INNER JOIN transaction_tag_cross_ref cr ON t.id = cr.tagId 
        |        WHERE cr.transactionId = ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          _item = TagEntity(_tmpId,_tmpName,_tmpColorHex)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTagsForTransactionFlow(transactionId: Long): Flow<List<TagEntity>> {
    val _sql: String = """
        |
        |        SELECT t.* FROM tags t 
        |        INNER JOIN transaction_tag_cross_ref cr ON t.id = cr.tagId 
        |        WHERE cr.transactionId = ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("tags", "transaction_tag_cross_ref")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          _item = TagEntity(_tmpId,_tmpName,_tmpColorHex)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllCrossRefsForTransaction(transactionId: Long) {
    val _sql: String = "DELETE FROM transaction_tag_cross_ref WHERE transactionId = ?"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
