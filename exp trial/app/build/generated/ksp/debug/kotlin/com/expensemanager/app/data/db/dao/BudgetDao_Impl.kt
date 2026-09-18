package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.BudgetEntity
import com.expensemanager.app.`data`.db.entity.BudgetPeriod
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
public class BudgetDao_Impl(
  __db: RoomDatabase,
) : BudgetDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBudgetEntity: EntityInsertAdapter<BudgetEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfBudgetEntity: EntityDeleteOrUpdateAdapter<BudgetEntity>

  private val __updateAdapterOfBudgetEntity: EntityDeleteOrUpdateAdapter<BudgetEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBudgetEntity = object : EntityInsertAdapter<BudgetEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `budgets` (`id`,`categoryId`,`amount`,`period`,`rolloverEnabled`,`rolloverAmount`,`startDate`,`alertThreshold`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetEntity) {
        statement.bindLong(1, entity.id)
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpCategoryId)
        }
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromBudgetPeriod(entity.period)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: Int = if (entity.rolloverEnabled) 1 else 0
        statement.bindLong(5, _tmp_2.toLong())
        val _tmp_3: String? = __converters.fromBigDecimal(entity.rolloverAmount)
        if (_tmp_3 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_3)
        }
        val _tmp_4: String? = __converters.fromLocalDate(entity.startDate)
        if (_tmp_4 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_4)
        }
        statement.bindLong(8, entity.alertThreshold.toLong())
      }
    }
    this.__deleteAdapterOfBudgetEntity = object : EntityDeleteOrUpdateAdapter<BudgetEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `budgets` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfBudgetEntity = object : EntityDeleteOrUpdateAdapter<BudgetEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `budgets` SET `id` = ?,`categoryId` = ?,`amount` = ?,`period` = ?,`rolloverEnabled` = ?,`rolloverAmount` = ?,`startDate` = ?,`alertThreshold` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetEntity) {
        statement.bindLong(1, entity.id)
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpCategoryId)
        }
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        val _tmp_1: String? = __converters.fromBudgetPeriod(entity.period)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: Int = if (entity.rolloverEnabled) 1 else 0
        statement.bindLong(5, _tmp_2.toLong())
        val _tmp_3: String? = __converters.fromBigDecimal(entity.rolloverAmount)
        if (_tmp_3 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_3)
        }
        val _tmp_4: String? = __converters.fromLocalDate(entity.startDate)
        if (_tmp_4 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_4)
        }
        statement.bindLong(8, entity.alertThreshold.toLong())
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insert(budget: BudgetEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfBudgetEntity.insertAndReturnId(_connection, budget)
    _result
  }

  public override suspend fun delete(budget: BudgetEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfBudgetEntity.handle(_connection, budget)
  }

  public override suspend fun update(budget: BudgetEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfBudgetEntity.handle(_connection, budget)
  }

  public override suspend fun getById(id: Long): BudgetEntity? {
    val _sql: String = "SELECT * FROM budgets WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: BudgetEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _result =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<BudgetEntity>> {
    val _sql: String = "SELECT * FROM budgets ORDER BY categoryId"
    return createFlow(__db, false, arrayOf("budgets")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: MutableList<BudgetEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BudgetEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _item =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<BudgetEntity> {
    val _sql: String = "SELECT * FROM budgets ORDER BY categoryId"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: MutableList<BudgetEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BudgetEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _item =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getOverallBudgetFlow(): Flow<BudgetEntity?> {
    val _sql: String = "SELECT * FROM budgets WHERE categoryId IS NULL LIMIT 1"
    return createFlow(__db, false, arrayOf("budgets")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: BudgetEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _result =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getOverallBudget(): BudgetEntity? {
    val _sql: String = "SELECT * FROM budgets WHERE categoryId IS NULL LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: BudgetEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _result =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByCategoryId(categoryId: Long): BudgetEntity? {
    val _sql: String = "SELECT * FROM budgets WHERE categoryId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: BudgetEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _result =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByCategoryIdFlow(categoryId: Long): Flow<BudgetEntity?> {
    val _sql: String = "SELECT * FROM budgets WHERE categoryId = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("budgets")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfPeriod: Int = getColumnIndexOrThrow(_stmt, "period")
        val _columnIndexOfRolloverEnabled: Int = getColumnIndexOrThrow(_stmt, "rolloverEnabled")
        val _columnIndexOfRolloverAmount: Int = getColumnIndexOrThrow(_stmt, "rolloverAmount")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfAlertThreshold: Int = getColumnIndexOrThrow(_stmt, "alertThreshold")
        val _result: BudgetEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
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
          val _tmpPeriod: BudgetPeriod
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPeriod)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPeriod)
          }
          val _tmp_3: BudgetPeriod? = __converters.toBudgetPeriod(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.BudgetPeriod', but it was NULL.")
          } else {
            _tmpPeriod = _tmp_3
          }
          val _tmpRolloverEnabled: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfRolloverEnabled).toInt()
          _tmpRolloverEnabled = _tmp_4 != 0
          val _tmpRolloverAmount: BigDecimal
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfRolloverAmount)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfRolloverAmount)
          }
          val _tmp_6: BigDecimal? = __converters.toBigDecimal(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpRolloverAmount = _tmp_6
          }
          val _tmpStartDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfStartDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_8
          }
          val _tmpAlertThreshold: Int
          _tmpAlertThreshold = _stmt.getLong(_columnIndexOfAlertThreshold).toInt()
          _result =
              BudgetEntity(_tmpId,_tmpCategoryId,_tmpAmount,_tmpPeriod,_tmpRolloverEnabled,_tmpRolloverAmount,_tmpStartDate,_tmpAlertThreshold)
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
