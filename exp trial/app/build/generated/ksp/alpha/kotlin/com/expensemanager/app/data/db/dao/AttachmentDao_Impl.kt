package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.AttachmentEntity
import java.time.LocalDateTime
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
public class AttachmentDao_Impl(
  __db: RoomDatabase,
) : AttachmentDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAttachmentEntity: EntityInsertAdapter<AttachmentEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfAttachmentEntity: EntityDeleteOrUpdateAdapter<AttachmentEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAttachmentEntity = object : EntityInsertAdapter<AttachmentEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `attachments` (`id`,`transactionId`,`fileName`,`filePath`,`mimeType`,`thumbnailPath`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AttachmentEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.transactionId)
        statement.bindText(3, entity.fileName)
        statement.bindText(4, entity.filePath)
        statement.bindText(5, entity.mimeType)
        val _tmpThumbnailPath: String? = entity.thumbnailPath
        if (_tmpThumbnailPath == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpThumbnailPath)
        }
        val _tmp: String? = __converters.fromLocalDateTime(entity.createdAt)
        if (_tmp == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp)
        }
      }
    }
    this.__deleteAdapterOfAttachmentEntity = object :
        EntityDeleteOrUpdateAdapter<AttachmentEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `attachments` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AttachmentEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insert(attachment: AttachmentEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfAttachmentEntity.insertAndReturnId(_connection, attachment)
    _result
  }

  public override suspend fun insertAll(attachments: List<AttachmentEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfAttachmentEntity.insert(_connection, attachments)
  }

  public override suspend fun delete(attachment: AttachmentEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfAttachmentEntity.handle(_connection, attachment)
  }

  public override fun getByTransactionFlow(transactionId: Long): Flow<List<AttachmentEntity>> {
    val _sql: String = "SELECT * FROM attachments WHERE transactionId = ? ORDER BY createdAt"
    return createFlow(__db, false, arrayOf("attachments")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfFileName: Int = getColumnIndexOrThrow(_stmt, "fileName")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "filePath")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfThumbnailPath: Int = getColumnIndexOrThrow(_stmt, "thumbnailPath")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<AttachmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AttachmentEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpFileName: String
          _tmpFileName = _stmt.getText(_columnIndexOfFileName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpMimeType: String
          _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          val _tmpThumbnailPath: String?
          if (_stmt.isNull(_columnIndexOfThumbnailPath)) {
            _tmpThumbnailPath = null
          } else {
            _tmpThumbnailPath = _stmt.getText(_columnIndexOfThumbnailPath)
          }
          val _tmpCreatedAt: LocalDateTime
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_1: LocalDateTime? = __converters.toLocalDateTime(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_1
          }
          _item =
              AttachmentEntity(_tmpId,_tmpTransactionId,_tmpFileName,_tmpFilePath,_tmpMimeType,_tmpThumbnailPath,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByTransaction(transactionId: Long): List<AttachmentEntity> {
    val _sql: String = "SELECT * FROM attachments WHERE transactionId = ? ORDER BY createdAt"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, transactionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfFileName: Int = getColumnIndexOrThrow(_stmt, "fileName")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "filePath")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfThumbnailPath: Int = getColumnIndexOrThrow(_stmt, "thumbnailPath")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<AttachmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AttachmentEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpFileName: String
          _tmpFileName = _stmt.getText(_columnIndexOfFileName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpMimeType: String
          _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          val _tmpThumbnailPath: String?
          if (_stmt.isNull(_columnIndexOfThumbnailPath)) {
            _tmpThumbnailPath = null
          } else {
            _tmpThumbnailPath = _stmt.getText(_columnIndexOfThumbnailPath)
          }
          val _tmpCreatedAt: LocalDateTime
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_1: LocalDateTime? = __converters.toLocalDateTime(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_1
          }
          _item =
              AttachmentEntity(_tmpId,_tmpTransactionId,_tmpFileName,_tmpFilePath,_tmpMimeType,_tmpThumbnailPath,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): AttachmentEntity? {
    val _sql: String = "SELECT * FROM attachments WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfFileName: Int = getColumnIndexOrThrow(_stmt, "fileName")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "filePath")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfThumbnailPath: Int = getColumnIndexOrThrow(_stmt, "thumbnailPath")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: AttachmentEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpFileName: String
          _tmpFileName = _stmt.getText(_columnIndexOfFileName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpMimeType: String
          _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          val _tmpThumbnailPath: String?
          if (_stmt.isNull(_columnIndexOfThumbnailPath)) {
            _tmpThumbnailPath = null
          } else {
            _tmpThumbnailPath = _stmt.getText(_columnIndexOfThumbnailPath)
          }
          val _tmpCreatedAt: LocalDateTime
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_1: LocalDateTime? = __converters.toLocalDateTime(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_1
          }
          _result =
              AttachmentEntity(_tmpId,_tmpTransactionId,_tmpFileName,_tmpFilePath,_tmpMimeType,_tmpThumbnailPath,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<AttachmentEntity> {
    val _sql: String = "SELECT * FROM attachments"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTransactionId: Int = getColumnIndexOrThrow(_stmt, "transactionId")
        val _columnIndexOfFileName: Int = getColumnIndexOrThrow(_stmt, "fileName")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "filePath")
        val _columnIndexOfMimeType: Int = getColumnIndexOrThrow(_stmt, "mimeType")
        val _columnIndexOfThumbnailPath: Int = getColumnIndexOrThrow(_stmt, "thumbnailPath")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<AttachmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AttachmentEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTransactionId: Long
          _tmpTransactionId = _stmt.getLong(_columnIndexOfTransactionId)
          val _tmpFileName: String
          _tmpFileName = _stmt.getText(_columnIndexOfFileName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpMimeType: String
          _tmpMimeType = _stmt.getText(_columnIndexOfMimeType)
          val _tmpThumbnailPath: String?
          if (_stmt.isNull(_columnIndexOfThumbnailPath)) {
            _tmpThumbnailPath = null
          } else {
            _tmpThumbnailPath = _stmt.getText(_columnIndexOfThumbnailPath)
          }
          val _tmpCreatedAt: LocalDateTime
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_1: LocalDateTime? = __converters.toLocalDateTime(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_1
          }
          _item =
              AttachmentEntity(_tmpId,_tmpTransactionId,_tmpFileName,_tmpFilePath,_tmpMimeType,_tmpThumbnailPath,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM attachments WHERE id = ?"
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

  public override suspend fun deleteByTransaction(transactionId: Long) {
    val _sql: String = "DELETE FROM attachments WHERE transactionId = ?"
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
