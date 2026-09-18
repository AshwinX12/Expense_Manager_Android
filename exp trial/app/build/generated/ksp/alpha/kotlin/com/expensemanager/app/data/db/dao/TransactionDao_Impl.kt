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
import com.expensemanager.app.`data`.db.entity.TransactionEntity
import com.expensemanager.app.`data`.db.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
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
public class TransactionDao_Impl(
  __db: RoomDatabase,
) : TransactionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTransactionEntity: EntityInsertAdapter<TransactionEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfTransactionEntity: EntityDeleteOrUpdateAdapter<TransactionEntity>

  private val __updateAdapterOfTransactionEntity: EntityDeleteOrUpdateAdapter<TransactionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTransactionEntity = object : EntityInsertAdapter<TransactionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `transactions` (`id`,`amount`,`currency`,`conversionRate`,`type`,`date`,`time`,`categoryId`,`subcategoryId`,`accountId`,`paymentMethod`,`note`,`merchantName`,`isRecurring`,`recurringRuleId`,`tags`,`hasAttachments`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionEntity) {
        statement.bindLong(1, entity.id)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmp)
        }
        statement.bindText(3, entity.currency)
        val _tmp_1: String? = __converters.fromBigDecimal(entity.conversionRate)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: String? = __converters.fromTransactionType(entity.type)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        val _tmp_3: String? = __converters.fromLocalDate(entity.date)
        if (_tmp_3 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_3)
        }
        val _tmpTime: LocalTime? = entity.time
        val _tmp_4: String? = __converters.fromLocalTime(_tmpTime)
        if (_tmp_4 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_4)
        }
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpCategoryId)
        }
        val _tmpSubcategoryId: Long? = entity.subcategoryId
        if (_tmpSubcategoryId == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpSubcategoryId)
        }
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpAccountId)
        }
        val _tmpPaymentMethod: PaymentMethod? = entity.paymentMethod
        val _tmp_5: String? = __converters.fromPaymentMethod(_tmpPaymentMethod)
        if (_tmp_5 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_5)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpNote)
        }
        val _tmpMerchantName: String? = entity.merchantName
        if (_tmpMerchantName == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpMerchantName)
        }
        val _tmp_6: Int = if (entity.isRecurring) 1 else 0
        statement.bindLong(14, _tmp_6.toLong())
        val _tmpRecurringRuleId: Long? = entity.recurringRuleId
        if (_tmpRecurringRuleId == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmpRecurringRuleId)
        }
        val _tmpTags: List<String>? = entity.tags
        val _tmp_7: String? = __converters.fromStringList(_tmpTags)
        if (_tmp_7 == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmp_7)
        }
        val _tmp_8: Int = if (entity.hasAttachments) 1 else 0
        statement.bindLong(17, _tmp_8.toLong())
        val _tmp_9: String? = __converters.fromLocalDateTime(entity.createdAt)
        if (_tmp_9 == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmp_9)
        }
        val _tmp_10: String? = __converters.fromLocalDateTime(entity.updatedAt)
        if (_tmp_10 == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmp_10)
        }
      }
    }
    this.__deleteAdapterOfTransactionEntity = object :
        EntityDeleteOrUpdateAdapter<TransactionEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `transactions` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfTransactionEntity = object :
        EntityDeleteOrUpdateAdapter<TransactionEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `transactions` SET `id` = ?,`amount` = ?,`currency` = ?,`conversionRate` = ?,`type` = ?,`date` = ?,`time` = ?,`categoryId` = ?,`subcategoryId` = ?,`accountId` = ?,`paymentMethod` = ?,`note` = ?,`merchantName` = ?,`isRecurring` = ?,`recurringRuleId` = ?,`tags` = ?,`hasAttachments` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionEntity) {
        statement.bindLong(1, entity.id)
        val _tmp: String? = __converters.fromBigDecimal(entity.amount)
        if (_tmp == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmp)
        }
        statement.bindText(3, entity.currency)
        val _tmp_1: String? = __converters.fromBigDecimal(entity.conversionRate)
        if (_tmp_1 == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp_1)
        }
        val _tmp_2: String? = __converters.fromTransactionType(entity.type)
        if (_tmp_2 == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp_2)
        }
        val _tmp_3: String? = __converters.fromLocalDate(entity.date)
        if (_tmp_3 == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmp_3)
        }
        val _tmpTime: LocalTime? = entity.time
        val _tmp_4: String? = __converters.fromLocalTime(_tmpTime)
        if (_tmp_4 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_4)
        }
        val _tmpCategoryId: Long? = entity.categoryId
        if (_tmpCategoryId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpCategoryId)
        }
        val _tmpSubcategoryId: Long? = entity.subcategoryId
        if (_tmpSubcategoryId == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpSubcategoryId)
        }
        val _tmpAccountId: Long? = entity.accountId
        if (_tmpAccountId == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpAccountId)
        }
        val _tmpPaymentMethod: PaymentMethod? = entity.paymentMethod
        val _tmp_5: String? = __converters.fromPaymentMethod(_tmpPaymentMethod)
        if (_tmp_5 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_5)
        }
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpNote)
        }
        val _tmpMerchantName: String? = entity.merchantName
        if (_tmpMerchantName == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpMerchantName)
        }
        val _tmp_6: Int = if (entity.isRecurring) 1 else 0
        statement.bindLong(14, _tmp_6.toLong())
        val _tmpRecurringRuleId: Long? = entity.recurringRuleId
        if (_tmpRecurringRuleId == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmpRecurringRuleId)
        }
        val _tmpTags: List<String>? = entity.tags
        val _tmp_7: String? = __converters.fromStringList(_tmpTags)
        if (_tmp_7 == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmp_7)
        }
        val _tmp_8: Int = if (entity.hasAttachments) 1 else 0
        statement.bindLong(17, _tmp_8.toLong())
        val _tmp_9: String? = __converters.fromLocalDateTime(entity.createdAt)
        if (_tmp_9 == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmp_9)
        }
        val _tmp_10: String? = __converters.fromLocalDateTime(entity.updatedAt)
        if (_tmp_10 == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmp_10)
        }
        statement.bindLong(20, entity.id)
      }
    }
  }

  public override suspend fun insert(transaction: TransactionEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfTransactionEntity.insertAndReturnId(_connection,
        transaction)
    _result
  }

  public override suspend fun delete(transaction: TransactionEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfTransactionEntity.handle(_connection, transaction)
  }

  public override suspend fun update(transaction: TransactionEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfTransactionEntity.handle(_connection, transaction)
  }

  public override suspend fun getById(id: Long): TransactionEntity? {
    val _sql: String = "SELECT * FROM transactions WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: TransactionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _result =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByIdFlow(id: Long): Flow<TransactionEntity?> {
    val _sql: String = "SELECT * FROM transactions WHERE id = ?"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: TransactionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _result =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllFlow(): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions ORDER BY date DESC, createdAt DESC"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<TransactionEntity> {
    val _sql: String = "SELECT * FROM transactions ORDER BY date DESC, createdAt DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getRecentFlow(limit: Int): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions ORDER BY date DESC, createdAt DESC LIMIT ?"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByDateRangeFlow(startDate: LocalDate, endDate: LocalDate):
      Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_3
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_5: BigDecimal? = __converters.toBigDecimal(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_5
          }
          val _tmpType: TransactionType
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_7: TransactionType? = __converters.toTransactionType(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_7
          }
          val _tmpDate: LocalDate
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_9: LocalDate? = __converters.toLocalDate(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_9
          }
          val _tmpTime: LocalTime?
          val _tmp_10: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_10)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
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
          val _tmpIsRecurring: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_12 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_13)
          val _tmpHasAttachments: Boolean
          val _tmp_14: Int
          _tmp_14 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_14 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_16
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_17: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_17 = null
          } else {
            _tmp_17 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_18: LocalDateTime? = __converters.toLocalDateTime(_tmp_17)
          if (_tmp_18 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_18
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByDateRange(startDate: LocalDate, endDate: LocalDate):
      List<TransactionEntity> {
    val _sql: String = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_3
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_5: BigDecimal? = __converters.toBigDecimal(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_5
          }
          val _tmpType: TransactionType
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_7: TransactionType? = __converters.toTransactionType(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_7
          }
          val _tmpDate: LocalDate
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_9: LocalDate? = __converters.toLocalDate(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_9
          }
          val _tmpTime: LocalTime?
          val _tmp_10: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_10)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
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
          val _tmpIsRecurring: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_12 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_13)
          val _tmpHasAttachments: Boolean
          val _tmp_14: Int
          _tmp_14 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_14 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_16
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_17: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_17 = null
          } else {
            _tmp_17 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_18: LocalDateTime? = __converters.toLocalDateTime(_tmp_17)
          if (_tmp_18 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_18
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByCategoryFlow(categoryId: Long): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions WHERE categoryId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByAccountFlow(accountId: Long): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions WHERE accountId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, accountId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByTypeFlow(type: TransactionType): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM transactions WHERE type = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromTransactionType(type)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_4: BigDecimal? = __converters.toBigDecimal(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_4
          }
          val _tmpType: TransactionType
          val _tmp_5: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_6: TransactionType? = __converters.toTransactionType(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_6
          }
          val _tmpDate: LocalDate
          val _tmp_7: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_8: LocalDate? = __converters.toLocalDate(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_8
          }
          val _tmpTime: LocalTime?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_9)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_10: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_10)
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
          val _tmpIsRecurring: Boolean
          val _tmp_11: Int
          _tmp_11 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_11 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_12: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_12 = null
          } else {
            _tmp_12 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_12)
          val _tmpHasAttachments: Boolean
          val _tmp_13: Int
          _tmp_13 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_13 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_14: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_14 = null
          } else {
            _tmp_14 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_15: LocalDateTime? = __converters.toLocalDateTime(_tmp_14)
          if (_tmp_15 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_15
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_16: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_16 = null
          } else {
            _tmp_16 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_17: LocalDateTime? = __converters.toLocalDateTime(_tmp_16)
          if (_tmp_17 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_17
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAccountBalanceFlow(accountId: Long): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END), 0) -
        |               COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END), 0) -
        |               COALESCE(SUM(CASE WHEN type = 'TRANSFER_TO_GOAL' THEN amount ELSE 0 END), 0) +
        |               COALESCE(SUM(CASE WHEN type = 'DEBT_TRANSFER_IN' THEN amount ELSE 0 END), 0) -
        |               COALESCE(SUM(CASE WHEN type = 'DEBT_TRANSFER_OUT' THEN amount ELSE 0 END), 0)
        |        FROM transactions WHERE accountId = ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, accountId)
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

  public override fun getTotalExpenseFlow(startDate: LocalDate, endDate: LocalDate):
      Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_2: String?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(0)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_3
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

  public override fun getTotalIncomeFlow(startDate: LocalDate, endDate: LocalDate):
      Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type = 'INCOME' AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_2: String?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(0)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_3
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

  public override fun getDailyExpenseFlow(date: LocalDate): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date = ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_1: String?
          if (_stmt.isNull(0)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(0)
          }
          val _tmp_2: BigDecimal? = __converters.toBigDecimal(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_2
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

  public override fun getCategoryExpenseFlow(
    categoryId: Long,
    startDate: LocalDate,
    endDate: LocalDate,
  ): Flow<BigDecimal> {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type = 'EXPENSE' AND categoryId = ? 
        |        AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        _argIndex = 2
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 3
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_2: String?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(0)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_3
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

  public override suspend fun getCategoryExpense(
    categoryId: Long,
    startDate: LocalDate,
    endDate: LocalDate,
  ): BigDecimal {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type = 'EXPENSE' AND categoryId = ? 
        |        AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, categoryId)
        _argIndex = 2
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 3
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_2: String?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(0)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_3
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

  public override suspend fun getTotalExpense(startDate: LocalDate, endDate: LocalDate):
      BigDecimal {
    val _sql: String = """
        |
        |        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        |        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _result: BigDecimal
        if (_stmt.step()) {
          val _tmp_2: String?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(0)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _result = _tmp_3
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

  public override fun searchFlow(query: String): Flow<List<TransactionEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM transactions 
        |        WHERE (note LIKE '%' || ? || '%' OR merchantName LIKE '%' || ? || '%')
        |        ORDER BY date DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
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
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpDate: LocalDate
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_7: LocalDate? = __converters.toLocalDate(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_7
          }
          val _tmpTime: LocalTime?
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_8)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
          }
          val _tmpAccountId: Long?
          if (_stmt.isNull(_columnIndexOfAccountId)) {
            _tmpAccountId = null
          } else {
            _tmpAccountId = _stmt.getLong(_columnIndexOfAccountId)
          }
          val _tmpPaymentMethod: PaymentMethod?
          val _tmp_9: String?
          if (_stmt.isNull(_columnIndexOfPaymentMethod)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getText(_columnIndexOfPaymentMethod)
          }
          _tmpPaymentMethod = __converters.toPaymentMethod(_tmp_9)
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
          val _tmpIsRecurring: Boolean
          val _tmp_10: Int
          _tmp_10 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_10 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_11: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_11 = null
          } else {
            _tmp_11 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_11)
          val _tmpHasAttachments: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_12 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_14: LocalDateTime? = __converters.toLocalDateTime(_tmp_13)
          if (_tmp_14 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_14
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_16
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByAmountRangeFlow(minAmount: BigDecimal, maxAmount: BigDecimal):
      Flow<List<TransactionEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM transactions 
        |        WHERE amount BETWEEN ? AND ? 
        |        ORDER BY date DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("transactions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromBigDecimal(minAmount)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromBigDecimal(maxAmount)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCurrency: Int = getColumnIndexOrThrow(_stmt, "currency")
        val _columnIndexOfConversionRate: Int = getColumnIndexOrThrow(_stmt, "conversionRate")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfTime: Int = getColumnIndexOrThrow(_stmt, "time")
        val _columnIndexOfCategoryId: Int = getColumnIndexOrThrow(_stmt, "categoryId")
        val _columnIndexOfSubcategoryId: Int = getColumnIndexOrThrow(_stmt, "subcategoryId")
        val _columnIndexOfAccountId: Int = getColumnIndexOrThrow(_stmt, "accountId")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfMerchantName: Int = getColumnIndexOrThrow(_stmt, "merchantName")
        val _columnIndexOfIsRecurring: Int = getColumnIndexOrThrow(_stmt, "isRecurring")
        val _columnIndexOfRecurringRuleId: Int = getColumnIndexOrThrow(_stmt, "recurringRuleId")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfHasAttachments: Int = getColumnIndexOrThrow(_stmt, "hasAttachments")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAmount: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfAmount)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfAmount)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpAmount = _tmp_3
          }
          val _tmpCurrency: String
          _tmpCurrency = _stmt.getText(_columnIndexOfCurrency)
          val _tmpConversionRate: BigDecimal
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfConversionRate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfConversionRate)
          }
          val _tmp_5: BigDecimal? = __converters.toBigDecimal(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRate = _tmp_5
          }
          val _tmpType: TransactionType
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_7: TransactionType? = __converters.toTransactionType(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_7
          }
          val _tmpDate: LocalDate
          val _tmp_8: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_9: LocalDate? = __converters.toLocalDate(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_9
          }
          val _tmpTime: LocalTime?
          val _tmp_10: String?
          if (_stmt.isNull(_columnIndexOfTime)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getText(_columnIndexOfTime)
          }
          _tmpTime = __converters.toLocalTime(_tmp_10)
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpSubcategoryId: Long?
          if (_stmt.isNull(_columnIndexOfSubcategoryId)) {
            _tmpSubcategoryId = null
          } else {
            _tmpSubcategoryId = _stmt.getLong(_columnIndexOfSubcategoryId)
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
          val _tmpIsRecurring: Boolean
          val _tmp_12: Int
          _tmp_12 = _stmt.getLong(_columnIndexOfIsRecurring).toInt()
          _tmpIsRecurring = _tmp_12 != 0
          val _tmpRecurringRuleId: Long?
          if (_stmt.isNull(_columnIndexOfRecurringRuleId)) {
            _tmpRecurringRuleId = null
          } else {
            _tmpRecurringRuleId = _stmt.getLong(_columnIndexOfRecurringRuleId)
          }
          val _tmpTags: List<String>?
          val _tmp_13: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmp_13 = null
          } else {
            _tmp_13 = _stmt.getText(_columnIndexOfTags)
          }
          _tmpTags = __converters.toStringList(_tmp_13)
          val _tmpHasAttachments: Boolean
          val _tmp_14: Int
          _tmp_14 = _stmt.getLong(_columnIndexOfHasAttachments).toInt()
          _tmpHasAttachments = _tmp_14 != 0
          val _tmpCreatedAt: LocalDateTime
          val _tmp_15: String?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_15 = null
          } else {
            _tmp_15 = _stmt.getText(_columnIndexOfCreatedAt)
          }
          val _tmp_16: LocalDateTime? = __converters.toLocalDateTime(_tmp_15)
          if (_tmp_16 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_16
          }
          val _tmpUpdatedAt: LocalDateTime
          val _tmp_17: String?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_17 = null
          } else {
            _tmp_17 = _stmt.getText(_columnIndexOfUpdatedAt)
          }
          val _tmp_18: LocalDateTime? = __converters.toLocalDateTime(_tmp_17)
          if (_tmp_18 == null) {
            error("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_18
          }
          _item =
              TransactionEntity(_tmpId,_tmpAmount,_tmpCurrency,_tmpConversionRate,_tmpType,_tmpDate,_tmpTime,_tmpCategoryId,_tmpSubcategoryId,_tmpAccountId,_tmpPaymentMethod,_tmpNote,_tmpMerchantName,_tmpIsRecurring,_tmpRecurringRuleId,_tmpTags,_tmpHasAttachments,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExpenseByCategoryRaw(startDate: LocalDate, endDate: LocalDate):
      List<CategoryTotal> {
    val _sql: String = """
        |
        |        SELECT categoryId, COALESCE(SUM(amount), 0) as total 
        |        FROM transactions 
        |        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN ? AND ? 
        |        GROUP BY categoryId
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfCategoryId: Int = 0
        val _columnIndexOfTotal: Int = 1
        val _result: MutableList<CategoryTotal> = mutableListOf()
        while (_stmt.step()) {
          val _item: CategoryTotal
          val _tmpCategoryId: Long?
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId)
          }
          val _tmpTotal: BigDecimal
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfTotal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfTotal)
          }
          val _tmp_3: BigDecimal? = __converters.toBigDecimal(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTotal = _tmp_3
          }
          _item = CategoryTotal(_tmpCategoryId,_tmpTotal)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDailyExpenseTotals(startDate: LocalDate, endDate: LocalDate):
      List<DateTotal> {
    val _sql: String = """
        |
        |        SELECT date, COALESCE(SUM(amount), 0) as total 
        |        FROM transactions 
        |        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN ? AND ? 
        |        GROUP BY date ORDER BY date
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfDate: Int = 0
        val _columnIndexOfTotal: Int = 1
        val _result: MutableList<DateTotal> = mutableListOf()
        while (_stmt.step()) {
          val _item: DateTotal
          val _tmpDate: LocalDate
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_3: LocalDate? = __converters.toLocalDate(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_3
          }
          val _tmpTotal: BigDecimal
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfTotal)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfTotal)
          }
          val _tmp_5: BigDecimal? = __converters.toBigDecimal(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTotal = _tmp_5
          }
          _item = DateTotal(_tmpDate,_tmpTotal)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMonthlyTotalsByType(startDate: LocalDate, endDate: LocalDate):
      List<DateTypeTotal> {
    val _sql: String = """
        |
        |        SELECT date, type, COALESCE(SUM(amount), 0) as total 
        |        FROM transactions 
        |        WHERE date BETWEEN ? AND ? 
        |        GROUP BY strftime('%Y-%m', date), type ORDER BY date
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.fromLocalDate(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        _argIndex = 2
        val _tmp_1: String? = __converters.fromLocalDate(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp_1)
        }
        val _columnIndexOfDate: Int = 0
        val _columnIndexOfType: Int = 1
        val _columnIndexOfTotal: Int = 2
        val _result: MutableList<DateTypeTotal> = mutableListOf()
        while (_stmt.step()) {
          val _item: DateTypeTotal
          val _tmpDate: LocalDate
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfDate)
          }
          val _tmp_3: LocalDate? = __converters.toLocalDate(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_3
          }
          val _tmpType: TransactionType
          val _tmp_4: String?
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getText(_columnIndexOfType)
          }
          val _tmp_5: TransactionType? = __converters.toTransactionType(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'com.expensemanager.app.`data`.db.entity.TransactionType', but it was NULL.")
          } else {
            _tmpType = _tmp_5
          }
          val _tmpTotal: BigDecimal
          val _tmp_6: String?
          if (_stmt.isNull(_columnIndexOfTotal)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getText(_columnIndexOfTotal)
          }
          val _tmp_7: BigDecimal? = __converters.toBigDecimal(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpTotal = _tmp_7
          }
          _item = DateTypeTotal(_tmpDate,_tmpType,_tmpTotal)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM transactions"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM transactions WHERE id = ?"
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
