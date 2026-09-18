package com.expensemanager.app.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.expensemanager.app.`data`.db.converter.Converters
import com.expensemanager.app.`data`.db.entity.CurrencyEntity
import java.math.BigDecimal
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
public class CurrencyDao_Impl(
  __db: RoomDatabase,
) : CurrencyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCurrencyEntity: EntityInsertAdapter<CurrencyEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfCurrencyEntity: EntityDeleteOrUpdateAdapter<CurrencyEntity>

  private val __updateAdapterOfCurrencyEntity: EntityDeleteOrUpdateAdapter<CurrencyEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfCurrencyEntity = object : EntityInsertAdapter<CurrencyEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `currencies` (`code`,`name`,`symbol`,`conversionRateToBase`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CurrencyEntity) {
        statement.bindText(1, entity.code)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.symbol)
        val _tmp: String? = __converters.fromBigDecimal(entity.conversionRateToBase)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
      }
    }
    this.__deleteAdapterOfCurrencyEntity = object : EntityDeleteOrUpdateAdapter<CurrencyEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `currencies` WHERE `code` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CurrencyEntity) {
        statement.bindText(1, entity.code)
      }
    }
    this.__updateAdapterOfCurrencyEntity = object : EntityDeleteOrUpdateAdapter<CurrencyEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `currencies` SET `code` = ?,`name` = ?,`symbol` = ?,`conversionRateToBase` = ? WHERE `code` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CurrencyEntity) {
        statement.bindText(1, entity.code)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.symbol)
        val _tmp: String? = __converters.fromBigDecimal(entity.conversionRateToBase)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmp)
        }
        statement.bindText(5, entity.code)
      }
    }
  }

  public override suspend fun insert(currency: CurrencyEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfCurrencyEntity.insert(_connection, currency)
  }

  public override suspend fun delete(currency: CurrencyEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfCurrencyEntity.handle(_connection, currency)
  }

  public override suspend fun update(currency: CurrencyEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfCurrencyEntity.handle(_connection, currency)
  }

  public override fun getAllFlow(): Flow<List<CurrencyEntity>> {
    val _sql: String = "SELECT * FROM currencies ORDER BY code"
    return createFlow(__db, false, arrayOf("currencies")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfCode: Int = getColumnIndexOrThrow(_stmt, "code")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSymbol: Int = getColumnIndexOrThrow(_stmt, "symbol")
        val _columnIndexOfConversionRateToBase: Int = getColumnIndexOrThrow(_stmt,
            "conversionRateToBase")
        val _result: MutableList<CurrencyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CurrencyEntity
          val _tmpCode: String
          _tmpCode = _stmt.getText(_columnIndexOfCode)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSymbol: String
          _tmpSymbol = _stmt.getText(_columnIndexOfSymbol)
          val _tmpConversionRateToBase: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfConversionRateToBase)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfConversionRateToBase)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRateToBase = _tmp_1
          }
          _item = CurrencyEntity(_tmpCode,_tmpName,_tmpSymbol,_tmpConversionRateToBase)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<CurrencyEntity> {
    val _sql: String = "SELECT * FROM currencies ORDER BY code"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfCode: Int = getColumnIndexOrThrow(_stmt, "code")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSymbol: Int = getColumnIndexOrThrow(_stmt, "symbol")
        val _columnIndexOfConversionRateToBase: Int = getColumnIndexOrThrow(_stmt,
            "conversionRateToBase")
        val _result: MutableList<CurrencyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CurrencyEntity
          val _tmpCode: String
          _tmpCode = _stmt.getText(_columnIndexOfCode)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSymbol: String
          _tmpSymbol = _stmt.getText(_columnIndexOfSymbol)
          val _tmpConversionRateToBase: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfConversionRateToBase)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfConversionRateToBase)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRateToBase = _tmp_1
          }
          _item = CurrencyEntity(_tmpCode,_tmpName,_tmpSymbol,_tmpConversionRateToBase)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByCode(code: String): CurrencyEntity? {
    val _sql: String = "SELECT * FROM currencies WHERE code = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, code)
        val _columnIndexOfCode: Int = getColumnIndexOrThrow(_stmt, "code")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfSymbol: Int = getColumnIndexOrThrow(_stmt, "symbol")
        val _columnIndexOfConversionRateToBase: Int = getColumnIndexOrThrow(_stmt,
            "conversionRateToBase")
        val _result: CurrencyEntity?
        if (_stmt.step()) {
          val _tmpCode: String
          _tmpCode = _stmt.getText(_columnIndexOfCode)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpSymbol: String
          _tmpSymbol = _stmt.getText(_columnIndexOfSymbol)
          val _tmpConversionRateToBase: BigDecimal
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfConversionRateToBase)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfConversionRateToBase)
          }
          val _tmp_1: BigDecimal? = __converters.toBigDecimal(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.")
          } else {
            _tmpConversionRateToBase = _tmp_1
          }
          _result = CurrencyEntity(_tmpCode,_tmpName,_tmpSymbol,_tmpConversionRateToBase)
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
