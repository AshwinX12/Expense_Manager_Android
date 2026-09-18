package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.GoalEntity
import java.math.BigDecimal
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
public class GoalDao_Impl(
  __db: RoomDatabase,
) : GoalDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfGoalEntity: EntityInsertAdapter<GoalEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfGoalEntity: EntityDeleteOrUpdateAdapter<GoalEntity>

  private val __updateAdapterOfGoalEntity: EntityDeleteOrUpdateAdapter<GoalEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfGoalEntity = object : EntityInsertAdapter<GoalEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `goals` (`id`,`name`,`targetAmount`,`currentAmount`,`targetDate`,`iconName`,`colorHex`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: GoalEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmp: String? = __converters.fromBigDecimal(entity.targetAmount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromBigDecimal(entity.currentAmount)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmpTargetDate: LocalDate? = entity.targetDate
        val _tmp_2: String? = __converters.fromLocalDate(_tmpTargetDate)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        statement.bindText(6, entity.iconName)
        statement.bindText(7, entity.colorHex)
        val _tmp_3: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(8, _tmp_3.toLong())
      }
    }
    this.__deleteAdapterOfGoalEntity = object : EntityDeleteOrUpdateAdapter<GoalEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `goals` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: GoalEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfGoalEntity = object : EntityDeleteOrUpdateAdapter<GoalEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `goals` SET `id` = ?,`name` = ?,`targetAmount` = ?,`currentAmount` = ?,`targetDate` = ?,`iconName` = ?,`colorHex` = ?,`isCompleted` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: GoalEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmp: String? = __converters.fromBigDecimal(entity.targetAmount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromBigDecimal(entity.currentAmount)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmpTargetDate: LocalDate? = entity.targetDate
        val _tmp_2: String? = __converters.fromLocalDate(_tmpTargetDate)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        statement.bindText(6, entity.iconName)
        statement.bindText(7, entity.colorHex)
        val _tmp_3: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(8, _tmp_3.toLong())
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insert(goal: GoalEntity): Long = performSuspending(__db, false, true)
      { _connection ->
    val _result: Long = __insertAdapterOfGoalEntity.insertAndReturnId(_connection, goal)
    _result
  }

  public override suspend fun delete(goal: GoalEntity): Unit = performSuspending(__db, false, true)
      { _connection ->
    __deleteAdapterOfGoalEntity.handle(_connection, goal)
  }

  public override suspend fun update(goal: GoalEntity): Unit = performSuspending(__db, false, true)
      { _connection ->
    __updateAdapterOfGoalEntity.handle(_connection, goal)
  }

  public override suspend fun getById(id: Long): GoalEntity? {
    val _sql: String = "SELECT * FROM goals WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetAmount: Int = getColumnIndexOrThrow(_stmt, "targetAmount")
        val _columnIndexOfCurrentAmount: Int = getColumnIndexOrThrow(_stmt, "currentAmount")
        val _columnIndexOfTargetDate: Int = getColumnIndexOrThrow(_stmt, "targetDate")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: GoalEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTargetAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTargetAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTargetAmount = _tmp_1
          }
          val _tmpCurrentAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfCurrentAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfCurrentAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpCurrentAmount = _tmp_3
          }
          val _tmpTargetDate: LocalDate?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTargetDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTargetDate)
          }
          _tmpTargetDate = __converters.toLocalDate(_tmp_4)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          _result =
              GoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrentAmount,_tmpTargetDate,_tmpIconName,_tmpColorHex,_tmpIsCompleted)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<GoalEntity>> {
    val _sql: String = "SELECT * FROM goals ORDER BY isCompleted, name"
    return createFlow(__db, false, arrayOf("goals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetAmount: Int = getColumnIndexOrThrow(_stmt, "targetAmount")
        val _columnIndexOfCurrentAmount: Int = getColumnIndexOrThrow(_stmt, "currentAmount")
        val _columnIndexOfTargetDate: Int = getColumnIndexOrThrow(_stmt, "targetDate")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: MutableList<GoalEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GoalEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTargetAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTargetAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTargetAmount = _tmp_1
          }
          val _tmpCurrentAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfCurrentAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfCurrentAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpCurrentAmount = _tmp_3
          }
          val _tmpTargetDate: LocalDate?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTargetDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTargetDate)
          }
          _tmpTargetDate = __converters.toLocalDate(_tmp_4)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          _item =
              GoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrentAmount,_tmpTargetDate,_tmpIconName,_tmpColorHex,_tmpIsCompleted)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<GoalEntity> {
    val _sql: String = "SELECT * FROM goals ORDER BY isCompleted, name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetAmount: Int = getColumnIndexOrThrow(_stmt, "targetAmount")
        val _columnIndexOfCurrentAmount: Int = getColumnIndexOrThrow(_stmt, "currentAmount")
        val _columnIndexOfTargetDate: Int = getColumnIndexOrThrow(_stmt, "targetDate")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: MutableList<GoalEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GoalEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTargetAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTargetAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTargetAmount = _tmp_1
          }
          val _tmpCurrentAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfCurrentAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfCurrentAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpCurrentAmount = _tmp_3
          }
          val _tmpTargetDate: LocalDate?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTargetDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTargetDate)
          }
          _tmpTargetDate = __converters.toLocalDate(_tmp_4)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          _item =
              GoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrentAmount,_tmpTargetDate,_tmpIconName,_tmpColorHex,_tmpIsCompleted)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveFlow(): Flow<List<GoalEntity>> {
    val _sql: String = "SELECT * FROM goals WHERE isCompleted = 0 ORDER BY name"
    return createFlow(__db, false, arrayOf("goals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetAmount: Int = getColumnIndexOrThrow(_stmt, "targetAmount")
        val _columnIndexOfCurrentAmount: Int = getColumnIndexOrThrow(_stmt, "currentAmount")
        val _columnIndexOfTargetDate: Int = getColumnIndexOrThrow(_stmt, "targetDate")
        val _columnIndexOfIconName: Int = getColumnIndexOrThrow(_stmt, "iconName")
        val _columnIndexOfColorHex: Int = getColumnIndexOrThrow(_stmt, "colorHex")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: MutableList<GoalEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GoalEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetAmount: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfTargetAmount)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfTargetAmount)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTargetAmount = _tmp_1
          }
          val _tmpCurrentAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfCurrentAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfCurrentAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpCurrentAmount = _tmp_3
          }
          val _tmpTargetDate: LocalDate?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTargetDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTargetDate)
          }
          _tmpTargetDate = __converters.toLocalDate(_tmp_4)
          val _tmpIconName: String
          _tmpIconName = _stmt.getText(_columnIndexOfIconName)
          val _tmpColorHex: String
          _tmpColorHex = _stmt.getText(_columnIndexOfColorHex)
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          _item =
              GoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrentAmount,_tmpTargetDate,_tmpIconName,_tmpColorHex,_tmpIsCompleted)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun addToGoal(goalId: Long, amount: BigDecimal) {
    val _sql: String = "UPDATE goals SET currentAmount = currentAmount + ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromBigDecimal(amount)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, goalId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markCompleted(goalId: Long) {
    val _sql: String = "UPDATE goals SET isCompleted = 1 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, goalId)
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
