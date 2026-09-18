package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.PaymentMethod
import com.expensemanager.app.`data`.db.entity.RecurringFrequency
import com.expensemanager.app.`data`.db.entity.RecurringRuleEntity
import com.expensemanager.app.`data`.db.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
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
public class RecurringRuleDao_Impl(
  __db: RoomDatabase,
) : RecurringRuleDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfRecurringRuleEntity: EntityInsertAdapter<RecurringRuleEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfRecurringRuleEntity: EntityDeleteOrUpdateAdapter<RecurringRuleEntity>

  private val __updateAdapterOfRecurringRuleEntity: EntityDeleteOrUpdateAdapter<RecurringRuleEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfRecurringRuleEntity = object : EntityInsertAdapter<RecurringRuleEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `recurring_rules` (`id`,`templateTransactionId`,`frequency`,`interval`,`nextOccurrence`,`time`,`endDate`,`isActive`,`amount`,`type`,`categoryId`,`accountId`,`paymentMethod`,`note`,`merchantName`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RecurringRuleEntity) {
        statement.bindLong(1, entity.id)
        val _tmpTemplateTransactionId: Long? = entity.templateTransactionId
        if (_tmpTemplateTransactionId == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpTemplateTransactionId)
        }
        val _tmp: String? = __converters.fromRecurringFrequency(entity.frequency)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindLong(4, entity.interval.toLong())
        val _tmp_1: String? = __converters.fromLocalDate(entity.nextOccurrence)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_1)
        }
        val _tmpTime: LocalTime? = entity.time
        val _tmp_2: String? = __converters.fromLocalTime(_tmpTime)
        if (_tmp_2 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_2)
        }
        val _tmpEndDate: LocalDate? = entity.endDate
        val _tmp_3: String? = __converters.fromLocalDate(_tmpEndDate)
        if (_tmp_3 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_3)
        }
        val _tmp_4: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_4.toLong())
        val _tmp_5: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp_5 == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmp_5)
        }
        val _tmp_6: String? = __converters.fromTransactionType(entity.type)
        if (_tmp_6 == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp_6)
        }
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpCategoryId)
        }
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpAccountId)
        }
        val _tmpPaymentMethod: PaymentMethod? = entity.paymentMethod
        val _tmp_7: String? = __converters.fromPaymentMethod(_tmpPaymentMethod)
        if (_tmp_7 == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmp_7)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpNote)
        }
        val _tmpMerchantName: String? = entity.merchantName
        if (_tmpMerchantName == null) {
          statement.bindNull(15)
        } else {
          statement.bindText(15, _tmpMerchantName)
        }
      }
    }
    this.__deleteAdapterOfRecurringRuleEntity = object :
        EntityDeleteOrUpdateAdapter<RecurringRuleEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `recurring_rules` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: RecurringRuleEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfRecurringRuleEntity = object :
        EntityDeleteOrUpdateAdapter<RecurringRuleEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `recurring_rules` SET `id` = ?,`templateTransactionId` = ?,`frequency` = ?,`interval` = ?,`nextOccurrence` = ?,`time` = ?,`endDate` = ?,`isActive` = ?,`amount` = ?,`type` = ?,`categoryId` = ?,`accountId` = ?,`paymentMethod` = ?,`note` = ?,`merchantName` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: RecurringRuleEntity) {
        statement.bindLong(1, entity.id)
        val _tmpTemplateTransactionId: Long? = entity.templateTransactionId
        if (_tmpTemplateTransactionId == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpTemplateTransactionId)
        }
        val _tmp: String? = __converters.fromRecurringFrequency(entity.frequency)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmp)
        }
        statement.bindLong(4, entity.interval.toLong())
        val _tmp_1: String? = __converters.fromLocalDate(entity.nextOccurrence)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_1)
        }
        val _tmpTime: LocalTime? = entity.time
        val _tmp_2: String? = __converters.fromLocalTime(_tmpTime)
        if (_tmp_2 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_2)
        }
        val _tmpEndDate: LocalDate? = entity.endDate
        val _tmp_3: String? = __converters.fromLocalDate(_tmpEndDate)
        if (_tmp_3 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_3)
        }
        val _tmp_4: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_4.toLong())
        val _tmp_5: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp_5 == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmp_5)
        }
        val _tmp_6: String? = __converters.fromTransactionType(entity.type)
        if (_tmp_6 == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp_6)
        }
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpCategoryId)
        }
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpAccountId)
        }
        val _tmpPaymentMethod: PaymentMethod? = entity.paymentMethod
        val _tmp_7: String? = __converters.fromPaymentMethod(_tmpPaymentMethod)
        if (_tmp_7 == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmp_7)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpNote)
        }
        val _tmpMerchantName: String? = entity.merchantName
        if (_tmpMerchantName == null) {
          statement.bindNull(15)
        } else {
          statement.bindText(15, _tmpMerchantName)
        }
        statement.bindLong(16, entity.id)
      }
    }
  }

  public override suspend fun insert(rule: RecurringRuleEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfRecurringRuleEntity.insertAndReturnId(_connection, rule)
    _result
  }

  public override suspend fun delete(rule: RecurringRuleEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfRecurringRuleEntity.handle(_connection, rule)
  }

  public override suspend fun update(rule: RecurringRuleEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfRecurringRuleEntity.handle(_connection, rule)
  }

  public override suspend fun getById(id: Long): RecurringRuleEntity? {
    val _sql: String = "SELECT * FROM recurring_rules WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateTransactionId: Int = getColumnIndexOrThrow(_stmt,
            "templateTransactionId")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfInterval: Int = getColumnIndexOrThrow(_stmt, "interval")
        val _columnIndexOfNextOccurrence: Int = getColumnIndexOrThrow(_stmt, "nextOccurrence")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _result: RecurringRuleEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateTransactionId: Long?
          if (_stmt.isNull(_columnIndexOfTemplateTransactionId)) {
            _tmpTemplateTransactionId = null
          } else {
            _tmpTemplateTransactionId = _stmt.getLong(_columnIndexOfTemplateTransactionId)
          }
          val _tmpFrequency: RecurringFrequency
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfFrequency)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfFrequency)
          }
          val _tmp_1: RecurringFrequency? = __converters.toRecurringFrequency(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.RecurringFrequency', but it was NULL.")
          } else {
            _tmpFrequency = _tmp_1
          }
          val _tmpInterval: Int
          _tmpInterval = _stmt.getLong(_columnIndexOfInterval).toInt()
          val _tmpNextOccurrence: LocalDate
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfNextOccurrence)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfNextOccurrence)
          }
          val _tmp_3: LocalDate? = __converters.toLocalDate(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpNextOccurrence = _tmp_3
          }
          val _tmpTime: LocalTime?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_4)
          val _tmpEndDate: LocalDate?
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfEndDate)
          }
          _tmpEndDate = __converters.toLocalDate(_tmp_5)
          val _tmpIsActive: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_6 != 0
          val _tmpAmount: BigDecimal
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_8: BigDecimal? = __converters.toBigDecimal(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_8
          }
          val _tmpType: TransactionType
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_10: TransactionType? = __converters.toTransactionType(_tmp_9)
          if (_tmp_10 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_10
          }
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_11)
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpMerchantName: String?
          if (_stmt.isNull(_columnIndexOfMerchantName)) {
            _tmpMerchantName = null
          } else {
            _tmpMerchantName = _stmt.getText(_columnIndexOfMerchantName)
          }
          _result =
              RecurringRuleEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpInterval,_tmpNextOccurrence,_tmpTime,_tmpEndDate,_tmpIsActive,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<RecurringRuleEntity>> {
    val _sql: String = "SELECT * FROM recurring_rules ORDER BY nextOccurrence"
    return createFlow(__db, false, arrayOf("recurring_rules")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateTransactionId: Int = getColumnIndexOrThrow(_stmt,
            "templateTransactionId")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfInterval: Int = getColumnIndexOrThrow(_stmt, "interval")
        val _columnIndexOfNextOccurrence: Int = getColumnIndexOrThrow(_stmt, "nextOccurrence")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _result: MutableList<RecurringRuleEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RecurringRuleEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateTransactionId: Long?
          if (_stmt.isNull(_columnIndexOfTemplateTransactionId)) {
            _tmpTemplateTransactionId = null
          } else {
            _tmpTemplateTransactionId = _stmt.getLong(_columnIndexOfTemplateTransactionId)
          }
          val _tmpFrequency: RecurringFrequency
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfFrequency)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfFrequency)
          }
          val _tmp_1: RecurringFrequency? = __converters.toRecurringFrequency(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.RecurringFrequency', but it was NULL.")
          } else {
            _tmpFrequency = _tmp_1
          }
          val _tmpInterval: Int
          _tmpInterval = _stmt.getLong(_columnIndexOfInterval).toInt()
          val _tmpNextOccurrence: LocalDate
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfNextOccurrence)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfNextOccurrence)
          }
          val _tmp_3: LocalDate? = __converters.toLocalDate(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpNextOccurrence = _tmp_3
          }
          val _tmpTime: LocalTime?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_4)
          val _tmpEndDate: LocalDate?
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfEndDate)
          }
          _tmpEndDate = __converters.toLocalDate(_tmp_5)
          val _tmpIsActive: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_6 != 0
          val _tmpAmount: BigDecimal
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_8: BigDecimal? = __converters.toBigDecimal(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_8
          }
          val _tmpType: TransactionType
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_10: TransactionType? = __converters.toTransactionType(_tmp_9)
          if (_tmp_10 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_10
          }
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_11)
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpMerchantName: String?
          if (_stmt.isNull(_columnIndexOfMerchantName)) {
            _tmpMerchantName = null
          } else {
            _tmpMerchantName = _stmt.getText(_columnIndexOfMerchantName)
          }
          _item =
              RecurringRuleEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpInterval,_tmpNextOccurrence,_tmpTime,_tmpEndDate,_tmpIsActive,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDueRules(date: LocalDate): List<RecurringRuleEntity> {
    val _sql: String = "SELECT * FROM recurring_rules WHERE isActive = 1 AND nextOccurrence <= ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateTransactionId: Int = getColumnIndexOrThrow(_stmt,
            "templateTransactionId")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfInterval: Int = getColumnIndexOrThrow(_stmt, "interval")
        val _columnIndexOfNextOccurrence: Int = getColumnIndexOrThrow(_stmt, "nextOccurrence")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _result: MutableList<RecurringRuleEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RecurringRuleEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateTransactionId: Long?
          if (_stmt.isNull(_columnIndexOfTemplateTransactionId)) {
            _tmpTemplateTransactionId = null
          } else {
            _tmpTemplateTransactionId = _stmt.getLong(_columnIndexOfTemplateTransactionId)
          }
          val _tmpFrequency: RecurringFrequency
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfFrequency)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfFrequency)
          }
          val _tmp_2: RecurringFrequency? = __converters.toRecurringFrequency(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.RecurringFrequency', but it was NULL.")
          } else {
            _tmpFrequency = _tmp_2
          }
          val _tmpInterval: Int
          _tmpInterval = _stmt.getLong(_columnIndexOfInterval).toInt()
          val _tmpNextOccurrence: LocalDate
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfNextOccurrence)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfNextOccurrence)
          }
          val _tmp_4: LocalDate? = __converters.toLocalDate(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpNextOccurrence = _tmp_4
          }
          val _tmpTime: LocalTime?
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_5)
          val _tmpEndDate: LocalDate?
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfEndDate)
          }
          _tmpEndDate = __converters.toLocalDate(_tmp_6)
          val _tmpIsActive: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_7 != 0
          val _tmpAmount: BigDecimal
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_9: BigDecimal? = __converters.toBigDecimal(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_9
          }
          val _tmpType: TransactionType
          val _tmp_10: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_11: TransactionType? = __converters.toTransactionType(_tmp_10)
          if (_tmp_11 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_11
          }
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_12: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_12 = null
          } else {
            _tmp_12 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_12)
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpMerchantName: String?
          if (_stmt.isNull(_columnIndexOfMerchantName)) {
            _tmpMerchantName = null
          } else {
            _tmpMerchantName = _stmt.getText(_columnIndexOfMerchantName)
          }
          _item =
              RecurringRuleEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpInterval,_tmpNextOccurrence,_tmpTime,_tmpEndDate,_tmpIsActive,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveFlow(): Flow<List<RecurringRuleEntity>> {
    val _sql: String = "SELECT * FROM recurring_rules WHERE isActive = 1 ORDER BY nextOccurrence"
    return createFlow(__db, false, arrayOf("recurring_rules")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateTransactionId: Int = getColumnIndexOrThrow(_stmt,
            "templateTransactionId")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfInterval: Int = getColumnIndexOrThrow(_stmt, "interval")
        val _columnIndexOfNextOccurrence: Int = getColumnIndexOrThrow(_stmt, "nextOccurrence")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _result: MutableList<RecurringRuleEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RecurringRuleEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateTransactionId: Long?
          if (_stmt.isNull(_columnIndexOfTemplateTransactionId)) {
            _tmpTemplateTransactionId = null
          } else {
            _tmpTemplateTransactionId = _stmt.getLong(_columnIndexOfTemplateTransactionId)
          }
          val _tmpFrequency: RecurringFrequency
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfFrequency)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfFrequency)
          }
          val _tmp_1: RecurringFrequency? = __converters.toRecurringFrequency(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.RecurringFrequency', but it was NULL.")
          } else {
            _tmpFrequency = _tmp_1
          }
          val _tmpInterval: Int
          _tmpInterval = _stmt.getLong(_columnIndexOfInterval).toInt()
          val _tmpNextOccurrence: LocalDate
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfNextOccurrence)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfNextOccurrence)
          }
          val _tmp_3: LocalDate? = __converters.toLocalDate(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpNextOccurrence = _tmp_3
          }
          val _tmpTime: LocalTime?
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_4)
          val _tmpEndDate: LocalDate?
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfEndDate)
          }
          _tmpEndDate = __converters.toLocalDate(_tmp_5)
          val _tmpIsActive: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_6 != 0
          val _tmpAmount: BigDecimal
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_8: BigDecimal? = __converters.toBigDecimal(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_8
          }
          val _tmpType: TransactionType
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_10: TransactionType? = __converters.toTransactionType(_tmp_9)
          if (_tmp_10 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_10
          }
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_11)
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpMerchantName: String?
          if (_stmt.isNull(_columnIndexOfMerchantName)) {
            _tmpMerchantName = null
          } else {
            _tmpMerchantName = _stmt.getText(_columnIndexOfMerchantName)
          }
          _item =
              RecurringRuleEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpInterval,_tmpNextOccurrence,_tmpTime,_tmpEndDate,_tmpIsActive,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateNextOccurrence(id: Long, nextDate: LocalDate) {
    val _sql: String = "UPDATE recurring_rules SET nextOccurrence = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(nextDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setActive(id: Long, active: Boolean) {
    val _sql: String = "UPDATE recurring_rules SET isActive = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (active) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
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
