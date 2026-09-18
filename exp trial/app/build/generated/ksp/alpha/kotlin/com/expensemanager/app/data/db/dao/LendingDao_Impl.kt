package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.LendingEntity
import com.expensemanager.app.`data`.db.entity.LendingStatus
import com.expensemanager.app.`data`.db.entity.LendingType
import java.math.BigDecimal
import java.time.LocalDate
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
public class LendingDao_Impl(
  __db: RoomDatabase,
) : LendingDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLendingEntity: EntityInsertAdapter<LendingEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfLendingEntity: EntityDeleteOrUpdateAdapter<LendingEntity>

  private val __updateAdapterOfLendingEntity: EntityDeleteOrUpdateAdapter<LendingEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLendingEntity = object : EntityInsertAdapter<LendingEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `lending` (`id`,`personName`,`amount`,`type`,`date`,`note`,`status`,`repaidAmount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LendingEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.personName)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromLendingType(entity.type)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: String? = __converters.fromLocalDate(entity.date)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpNote)
        }
        val _tmp_3: String? = __converters.fromLendingStatus(entity.status)
        if (_tmp_3 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_3)
        }
        val _tmp_4: String? = __converters.fromBigDecimal(entity.repaidAmount)
        if (_tmp_4 == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmp_4)
        }
      }
    }
    this.__deleteAdapterOfLendingEntity = object : EntityDeleteOrUpdateAdapter<LendingEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `lending` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: LendingEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfLendingEntity = object : EntityDeleteOrUpdateAdapter<LendingEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `lending` SET `id` = ?,`personName` = ?,`amount` = ?,`type` = ?,`date` = ?,`note` = ?,`status` = ?,`repaidAmount` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: LendingEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.personName)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromLendingType(entity.type)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: String? = __converters.fromLocalDate(entity.date)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpNote)
        }
        val _tmp_3: String? = __converters.fromLendingStatus(entity.status)
        if (_tmp_3 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_3)
        }
        val _tmp_4: String? = __converters.fromBigDecimal(entity.repaidAmount)
        if (_tmp_4 == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmp_4)
        }
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insert(lending: LendingEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfLendingEntity.insertAndReturnId(_connection, lending)
    _result
  }

  public override suspend fun delete(lending: LendingEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfLendingEntity.handle(_connection, lending)
  }

  public override suspend fun update(lending: LendingEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfLendingEntity.handle(_connection, lending)
  }

  public override suspend fun getById(id: Long): LendingEntity? {
    val _sql: String = "SELECT * FROM lending WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfRepaidAmount: Int = getColumnIndexOrThrow(_stmt, "repaidAmount")
        val _result: LendingEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
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
          val _tmpType: LendingType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: LendingType? = __converters.toLendingType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpDate: LocalDate
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_5: LocalDate? = __converters.toLocalDate(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_5
          }
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpStatus: LendingStatus
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmp_7: LendingStatus? = __converters.toLendingStatus(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingStatus', but it was NULL.")
          } else {
            _tmpStatus = _tmp_7
          }
          val _tmpRepaidAmount: BigDecimal
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfRepaidAmount)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfRepaidAmount)
          }
          val _tmp_9: BigDecimal? = __converters.toBigDecimal(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRepaidAmount = _tmp_9
          }
          _result =
              LendingEntity(_tmpId,_tmpPersonName,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpStatus,_tmpRepaidAmount)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<LendingEntity>> {
    val _sql: String = "SELECT * FROM lending ORDER BY status, date DESC"
    return createFlow(__db, false, arrayOf("lending")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfRepaidAmount: Int = getColumnIndexOrThrow(_stmt, "repaidAmount")
        val _result: MutableList<LendingEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LendingEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
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
          val _tmpType: LendingType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: LendingType? = __converters.toLendingType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpDate: LocalDate
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_5: LocalDate? = __converters.toLocalDate(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_5
          }
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpStatus: LendingStatus
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmp_7: LendingStatus? = __converters.toLendingStatus(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingStatus', but it was NULL.")
          } else {
            _tmpStatus = _tmp_7
          }
          val _tmpRepaidAmount: BigDecimal
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfRepaidAmount)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfRepaidAmount)
          }
          val _tmp_9: BigDecimal? = __converters.toBigDecimal(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRepaidAmount = _tmp_9
          }
          _item =
              LendingEntity(_tmpId,_tmpPersonName,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpStatus,_tmpRepaidAmount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByTypeFlow(type: LendingType): Flow<List<LendingEntity>> {
    val _sql: String = "SELECT * FROM lending WHERE type = ? ORDER BY status, date DESC"
    return createFlow(__db, false, arrayOf("lending")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLendingType(type)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfRepaidAmount: Int = getColumnIndexOrThrow(_stmt, "repaidAmount")
        val _result: MutableList<LendingEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LendingEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
          val _tmpAmount: BigDecimal
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_2: BigDecimal? = __converters.toBigDecimal(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_2
          }
          val _tmpType: LendingType
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_4: LendingType? = __converters.toLendingType(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingType', but it was NULL.")
          } else {
            _tmpType = _tmp_4
          }
          val _tmpDate: LocalDate
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_6: LocalDate? = __converters.toLocalDate(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_6
          }
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpStatus: LendingStatus
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmp_8: LendingStatus? = __converters.toLendingStatus(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingStatus', but it was NULL.")
          } else {
            _tmpStatus = _tmp_8
          }
          val _tmpRepaidAmount: BigDecimal
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfRepaidAmount)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfRepaidAmount)
          }
          val _tmp_10: BigDecimal? = __converters.toBigDecimal(_tmp_9)
          if (_tmp_10 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRepaidAmount = _tmp_10
          }
          _item =
              LendingEntity(_tmpId,_tmpPersonName,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpStatus,_tmpRepaidAmount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByPersonFlow(name: String): Flow<List<LendingEntity>> {
    val _sql: String = "SELECT * FROM lending WHERE personName = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("lending")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPersonName: Int = getColumnIndexOrThrow(_stmt, "personName")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfRepaidAmount: Int = getColumnIndexOrThrow(_stmt, "repaidAmount")
        val _result: MutableList<LendingEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LendingEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPersonName: String
          _tmpPersonName = _stmt.getText(_columnIndexOfPersonName)
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
          val _tmpType: LendingType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: LendingType? = __converters.toLendingType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpDate: LocalDate
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_5: LocalDate? = __converters.toLocalDate(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_5
          }
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpStatus: LendingStatus
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmp_7: LendingStatus? = __converters.toLendingStatus(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.LendingStatus', but it was NULL.")
          } else {
            _tmpStatus = _tmp_7
          }
          val _tmpRepaidAmount: BigDecimal
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfRepaidAmount)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfRepaidAmount)
          }
          val _tmp_9: BigDecimal? = __converters.toBigDecimal(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRepaidAmount = _tmp_9
          }
          _item =
              LendingEntity(_tmpId,_tmpPersonName,_tmpAmount,_tmpType,_tmpDate,_tmpNote,_tmpStatus,_tmpRepaidAmount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTotalLentFlow(): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount - repaidAmount), 0) FROM lending 
        |        WHERE type = 'LENT' AND status != 'SETTLED'
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("lending")) { _connection ->
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

  public override fun getTotalBorrowedFlow(): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount - repaidAmount), 0) FROM lending 
        |        WHERE type = 'BORROWED' AND status != 'SETTLED'
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("lending")) { _connection ->
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

  public override suspend fun getAllPersonNames(): List<String> {
    val _sql: String = "SELECT DISTINCT personName FROM lending ORDER BY personName"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
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
