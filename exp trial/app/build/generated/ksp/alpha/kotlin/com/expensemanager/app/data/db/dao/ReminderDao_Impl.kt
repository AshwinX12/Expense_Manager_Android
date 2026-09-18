package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.ReminderEntity
import com.expensemanager.app.`data`.db.entity.ReminderType
import java.time.LocalDate
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
public class ReminderDao_Impl(
  __db: RoomDatabase,
) : ReminderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfReminderEntity: EntityInsertAdapter<ReminderEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfReminderEntity: EntityDeleteOrUpdateAdapter<ReminderEntity>

  private val __updateAdapterOfReminderEntity: EntityDeleteOrUpdateAdapter<ReminderEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfReminderEntity = object : EntityInsertAdapter<ReminderEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reminders` (`id`,`title`,`description`,`dueDate`,`leadTimeDays`,`type`,`recurringRuleId`,`isActive`,`isNotified`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReminderEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpDescription)
        }
        val _tmp: String? = __converters.fromLocalDate(entity.dueDate)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        statement.bindLong(5, entity.leadTimeDays.toLong())
        val _tmp_1: String? = __converters.fromReminderType(entity.type)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_1)
        }
        val _tmpRecurringRuleId: Long? = entity.recurringRuleId
        if (_tmpRecurringRuleId == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpRecurringRuleId)
        }
        val _tmp_2: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.isNotified) 1 else 0
        statement.bindLong(9, _tmp_3.toLong())
      }
    }
    this.__deleteAdapterOfReminderEntity = object : EntityDeleteOrUpdateAdapter<ReminderEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `reminders` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ReminderEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfReminderEntity = object : EntityDeleteOrUpdateAdapter<ReminderEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `reminders` SET `id` = ?,`title` = ?,`description` = ?,`dueDate` = ?,`leadTimeDays` = ?,`type` = ?,`recurringRuleId` = ?,`isActive` = ?,`isNotified` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ReminderEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpDescription)
        }
        val _tmp: String? = __converters.fromLocalDate(entity.dueDate)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        statement.bindLong(5, entity.leadTimeDays.toLong())
        val _tmp_1: String? = __converters.fromReminderType(entity.type)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_1)
        }
        val _tmpRecurringRuleId: Long? = entity.recurringRuleId
        if (_tmpRecurringRuleId == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpRecurringRuleId)
        }
        val _tmp_2: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.isNotified) 1 else 0
        statement.bindLong(9, _tmp_3.toLong())
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun insert(reminder: ReminderEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfReminderEntity.insertAndReturnId(_connection, reminder)
    _result
  }

  public override suspend fun delete(reminder: ReminderEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfReminderEntity.handle(_connection, reminder)
  }

  public override suspend fun update(reminder: ReminderEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfReminderEntity.handle(_connection, reminder)
  }

  public override suspend fun getById(id: Long): ReminderEntity? {
    val _sql: String = "SELECT * FROM reminders WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfLeadTimeDays: Int = getColumnIndexOrThrow(_stmt, "leadTimeDays")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsNotified: Int = getColumnIndexOrThrow(_stmt, "isNotified")
        val _result: ReminderEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpDueDate: LocalDate
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfDueDate)
          }
          val _tmp_1: LocalDate? = __converters.toLocalDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDueDate = _tmp_1
          }
          val _tmpLeadTimeDays: Int
          _tmpLeadTimeDays = _stmt.getLong(_columnIndexOfLeadTimeDays).toInt()
          val _tmpType: ReminderType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: ReminderType? = __converters.toReminderType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.ReminderType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpIsActive: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_4 != 0
          val _tmpIsNotified: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsNotified).toInt()
          _tmpIsNotified = _tmp_5 != 0
          _result =
              ReminderEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpLeadTimeDays,_tmpType,_tmpRecurringRuleId,_tmpIsActive,_tmpIsNotified)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveFlow(): Flow<List<ReminderEntity>> {
    val _sql: String = "SELECT * FROM reminders WHERE isActive = 1 ORDER BY dueDate"
    return createFlow(__db, false, arrayOf("reminders")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfLeadTimeDays: Int = getColumnIndexOrThrow(_stmt, "leadTimeDays")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsNotified: Int = getColumnIndexOrThrow(_stmt, "isNotified")
        val _result: MutableList<ReminderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ReminderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpDueDate: LocalDate
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfDueDate)
          }
          val _tmp_1: LocalDate? = __converters.toLocalDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDueDate = _tmp_1
          }
          val _tmpLeadTimeDays: Int
          _tmpLeadTimeDays = _stmt.getLong(_columnIndexOfLeadTimeDays).toInt()
          val _tmpType: ReminderType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: ReminderType? = __converters.toReminderType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.ReminderType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpIsActive: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_4 != 0
          val _tmpIsNotified: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsNotified).toInt()
          _tmpIsNotified = _tmp_5 != 0
          _item =
              ReminderEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpLeadTimeDays,_tmpType,_tmpRecurringRuleId,_tmpIsActive,_tmpIsNotified)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<ReminderEntity>> {
    val _sql: String = "SELECT * FROM reminders ORDER BY dueDate"
    return createFlow(__db, false, arrayOf("reminders")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfLeadTimeDays: Int = getColumnIndexOrThrow(_stmt, "leadTimeDays")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsNotified: Int = getColumnIndexOrThrow(_stmt, "isNotified")
        val _result: MutableList<ReminderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ReminderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpDueDate: LocalDate
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfDueDate)
          }
          val _tmp_1: LocalDate? = __converters.toLocalDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDueDate = _tmp_1
          }
          val _tmpLeadTimeDays: Int
          _tmpLeadTimeDays = _stmt.getLong(_columnIndexOfLeadTimeDays).toInt()
          val _tmpType: ReminderType
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_3: ReminderType? = __converters.toReminderType(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.ReminderType', but it was NULL.")
          } else {
            _tmpType = _tmp_3
          }
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpIsActive: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_4 != 0
          val _tmpIsNotified: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsNotified).toInt()
          _tmpIsNotified = _tmp_5 != 0
          _item =
              ReminderEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpLeadTimeDays,_tmpType,_tmpRecurringRuleId,_tmpIsActive,_tmpIsNotified)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDueReminders(checkDate: LocalDate): List<ReminderEntity> {
    val _sql: String = """
        |
        |        SELECT * FROM reminders 
        |        WHERE isActive = 1 AND isNotified = 0 
        |        AND dueDate <= ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(checkDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfLeadTimeDays: Int = getColumnIndexOrThrow(_stmt, "leadTimeDays")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsNotified: Int = getColumnIndexOrThrow(_stmt, "isNotified")
        val _result: MutableList<ReminderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ReminderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpDueDate: LocalDate
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfDueDate)
          }
          val _tmp_2: LocalDate? = __converters.toLocalDate(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDueDate = _tmp_2
          }
          val _tmpLeadTimeDays: Int
          _tmpLeadTimeDays = _stmt.getLong(_columnIndexOfLeadTimeDays).toInt()
          val _tmpType: ReminderType
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_4: ReminderType? = __converters.toReminderType(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.ReminderType', but it was NULL.")
          } else {
            _tmpType = _tmp_4
          }
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpIsActive: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_5 != 0
          val _tmpIsNotified: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsNotified).toInt()
          _tmpIsNotified = _tmp_6 != 0
          _item =
              ReminderEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpLeadTimeDays,_tmpType,_tmpRecurringRuleId,_tmpIsActive,_tmpIsNotified)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markNotified(id: Long) {
    val _sql: String = "UPDATE reminders SET isNotified = 1 WHERE id = ?"
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
