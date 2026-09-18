package com.expensemanager.app.`data`.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.expensemanager.app.`data`.db.dao.AccountDao
import com.expensemanager.app.`data`.db.dao.AccountDao_Impl
import com.expensemanager.app.`data`.db.dao.AttachmentDao
import com.expensemanager.app.`data`.db.dao.AttachmentDao_Impl
import com.expensemanager.app.`data`.db.dao.BudgetDao
import com.expensemanager.app.`data`.db.dao.BudgetDao_Impl
import com.expensemanager.app.`data`.db.dao.CategoryDao
import com.expensemanager.app.`data`.db.dao.CategoryDao_Impl
import com.expensemanager.app.`data`.db.dao.CurrencyDao
import com.expensemanager.app.`data`.db.dao.CurrencyDao_Impl
import com.expensemanager.app.`data`.db.dao.GoalDao
import com.expensemanager.app.`data`.db.dao.GoalDao_Impl
import com.expensemanager.app.`data`.db.dao.LendingDao
import com.expensemanager.app.`data`.db.dao.LendingDao_Impl
import com.expensemanager.app.`data`.db.dao.QuickAddShortcutDao
import com.expensemanager.app.`data`.db.dao.QuickAddShortcutDao_Impl
import com.expensemanager.app.`data`.db.dao.RecurringRuleDao
import com.expensemanager.app.`data`.db.dao.RecurringRuleDao_Impl
import com.expensemanager.app.`data`.db.dao.ReminderDao
import com.expensemanager.app.`data`.db.dao.ReminderDao_Impl
import com.expensemanager.app.`data`.db.dao.SavedFilterDao
import com.expensemanager.app.`data`.db.dao.SavedFilterDao_Impl
import com.expensemanager.app.`data`.db.dao.SettingsDao
import com.expensemanager.app.`data`.db.dao.SettingsDao_Impl
import com.expensemanager.app.`data`.db.dao.SplitExpenseDao
import com.expensemanager.app.`data`.db.dao.SplitExpenseDao_Impl
import com.expensemanager.app.`data`.db.dao.SubcategoryDao
import com.expensemanager.app.`data`.db.dao.SubcategoryDao_Impl
import com.expensemanager.app.`data`.db.dao.TagDao
import com.expensemanager.app.`data`.db.dao.TagDao_Impl
import com.expensemanager.app.`data`.db.dao.TransactionDao
import com.expensemanager.app.`data`.db.dao.TransactionDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _transactionDao: Lazy<TransactionDao> = lazy {
    TransactionDao_Impl(this)
  }

  private val _categoryDao: Lazy<CategoryDao> = lazy {
    CategoryDao_Impl(this)
  }

  private val _subcategoryDao: Lazy<SubcategoryDao> = lazy {
    SubcategoryDao_Impl(this)
  }

  private val _accountDao: Lazy<AccountDao> = lazy {
    AccountDao_Impl(this)
  }

  private val _tagDao: Lazy<TagDao> = lazy {
    TagDao_Impl(this)
  }

  private val _budgetDao: Lazy<BudgetDao> = lazy {
    BudgetDao_Impl(this)
  }

  private val _goalDao: Lazy<GoalDao> = lazy {
    GoalDao_Impl(this)
  }

  private val _recurringRuleDao: Lazy<RecurringRuleDao> = lazy {
    RecurringRuleDao_Impl(this)
  }

  private val _reminderDao: Lazy<ReminderDao> = lazy {
    ReminderDao_Impl(this)
  }

  private val _lendingDao: Lazy<LendingDao> = lazy {
    LendingDao_Impl(this)
  }

  private val _splitExpenseDao: Lazy<SplitExpenseDao> = lazy {
    SplitExpenseDao_Impl(this)
  }

  private val _quickAddShortcutDao: Lazy<QuickAddShortcutDao> = lazy {
    QuickAddShortcutDao_Impl(this)
  }

  private val _savedFilterDao: Lazy<SavedFilterDao> = lazy {
    SavedFilterDao_Impl(this)
  }

  private val _currencyDao: Lazy<CurrencyDao> = lazy {
    CurrencyDao_Impl(this)
  }

  private val _attachmentDao: Lazy<AttachmentDao> = lazy {
    AttachmentDao_Impl(this)
  }

  private val _settingsDao: Lazy<SettingsDao> = lazy {
    SettingsDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(4,
        "04b7cf9cddaf7be96174d00b8c372223", "3b923b40edf662df87e06a3c55508ba1") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `amount` TEXT NOT NULL, `currency` TEXT NOT NULL, `conversionRate` TEXT NOT NULL, `type` TEXT NOT NULL, `date` TEXT NOT NULL, `time` TEXT, `categoryId` INTEGER, `subcategoryId` INTEGER, `accountId` INTEGER, `paymentMethod` TEXT, `note` TEXT, `merchantName` TEXT, `isRecurring` INTEGER NOT NULL, `recurringRuleId` INTEGER, `tags` TEXT, `hasAttachments` INTEGER NOT NULL, `createdAt` TEXT NOT NULL, `updatedAt` TEXT NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`subcategoryId`) REFERENCES `subcategories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`accountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_categoryId` ON `transactions` (`categoryId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_subcategoryId` ON `transactions` (`subcategoryId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_accountId` ON `transactions` (`accountId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_date` ON `transactions` (`date`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_type` ON `transactions` (`type`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `iconName` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `isDefault` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `subcategories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `parentCategoryId` INTEGER NOT NULL, FOREIGN KEY(`parentCategoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_subcategories_parentCategoryId` ON `subcategories` (`parentCategoryId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `accounts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `currency` TEXT NOT NULL, `initialBalance` TEXT NOT NULL, `iconName` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `isDefault` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tags` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `colorHex` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `transaction_tag_cross_ref` (`transactionId` INTEGER NOT NULL, `tagId` INTEGER NOT NULL, PRIMARY KEY(`transactionId`, `tagId`), FOREIGN KEY(`transactionId`) REFERENCES `transactions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tagId`) REFERENCES `tags`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transaction_tag_cross_ref_transactionId` ON `transaction_tag_cross_ref` (`transactionId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_transaction_tag_cross_ref_tagId` ON `transaction_tag_cross_ref` (`tagId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `budgets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoryId` INTEGER, `amount` TEXT NOT NULL, `period` TEXT NOT NULL, `rolloverEnabled` INTEGER NOT NULL, `rolloverAmount` TEXT NOT NULL, `startDate` TEXT NOT NULL, `alertThreshold` INTEGER NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_budgets_categoryId` ON `budgets` (`categoryId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `goals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `targetAmount` TEXT NOT NULL, `currentAmount` TEXT NOT NULL, `targetDate` TEXT, `iconName` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `recurring_rules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `templateTransactionId` INTEGER, `frequency` TEXT NOT NULL, `interval` INTEGER NOT NULL, `nextOccurrence` TEXT NOT NULL, `time` TEXT, `endDate` TEXT, `isActive` INTEGER NOT NULL, `amount` TEXT NOT NULL, `type` TEXT NOT NULL, `categoryId` INTEGER, `accountId` INTEGER, `paymentMethod` TEXT, `note` TEXT, `merchantName` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `dueDate` TEXT NOT NULL, `leadTimeDays` INTEGER NOT NULL, `type` TEXT NOT NULL, `recurringRuleId` INTEGER, `isActive` INTEGER NOT NULL, `isNotified` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `lending` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `personName` TEXT NOT NULL, `amount` TEXT NOT NULL, `type` TEXT NOT NULL, `date` TEXT NOT NULL, `note` TEXT, `status` TEXT NOT NULL, `repaidAmount` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `split_expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `transactionId` INTEGER NOT NULL, `personName` TEXT NOT NULL, `shareAmount` TEXT NOT NULL, `isSettled` INTEGER NOT NULL, FOREIGN KEY(`transactionId`) REFERENCES `transactions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_split_expenses_transactionId` ON `split_expenses` (`transactionId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `quick_add_shortcuts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `amount` TEXT NOT NULL, `categoryId` INTEGER NOT NULL, `accountId` INTEGER, `iconName` TEXT NOT NULL, `sortOrder` INTEGER NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`accountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_quick_add_shortcuts_categoryId` ON `quick_add_shortcuts` (`categoryId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_quick_add_shortcuts_accountId` ON `quick_add_shortcuts` (`accountId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `saved_filters` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `filterJson` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `currencies` (`code` TEXT NOT NULL, `name` TEXT NOT NULL, `symbol` TEXT NOT NULL, `conversionRateToBase` TEXT NOT NULL, PRIMARY KEY(`code`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `attachments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `transactionId` INTEGER NOT NULL, `fileName` TEXT NOT NULL, `filePath` TEXT NOT NULL, `mimeType` TEXT NOT NULL, `thumbnailPath` TEXT, `createdAt` TEXT NOT NULL, FOREIGN KEY(`transactionId`) REFERENCES `transactions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_attachments_transactionId` ON `attachments` (`transactionId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `settings` (`key` TEXT NOT NULL, `value` TEXT NOT NULL, PRIMARY KEY(`key`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '04b7cf9cddaf7be96174d00b8c372223')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `transactions`")
        connection.execSQL("DROP TABLE IF EXISTS `categories`")
        connection.execSQL("DROP TABLE IF EXISTS `subcategories`")
        connection.execSQL("DROP TABLE IF EXISTS `accounts`")
        connection.execSQL("DROP TABLE IF EXISTS `tags`")
        connection.execSQL("DROP TABLE IF EXISTS `transaction_tag_cross_ref`")
        connection.execSQL("DROP TABLE IF EXISTS `budgets`")
        connection.execSQL("DROP TABLE IF EXISTS `goals`")
        connection.execSQL("DROP TABLE IF EXISTS `recurring_rules`")
        connection.execSQL("DROP TABLE IF EXISTS `reminders`")
        connection.execSQL("DROP TABLE IF EXISTS `lending`")
        connection.execSQL("DROP TABLE IF EXISTS `split_expenses`")
        connection.execSQL("DROP TABLE IF EXISTS `quick_add_shortcuts`")
        connection.execSQL("DROP TABLE IF EXISTS `saved_filters`")
        connection.execSQL("DROP TABLE IF EXISTS `currencies`")
        connection.execSQL("DROP TABLE IF EXISTS `attachments`")
        connection.execSQL("DROP TABLE IF EXISTS `settings`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsTransactions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTransactions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("amount", TableInfo.Column("amount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("currency", TableInfo.Column("currency", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("conversionRate", TableInfo.Column("conversionRate", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("date", TableInfo.Column("date", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("time", TableInfo.Column("time", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("categoryId", TableInfo.Column("categoryId", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("subcategoryId", TableInfo.Column("subcategoryId", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("accountId", TableInfo.Column("accountId", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("paymentMethod", TableInfo.Column("paymentMethod", "TEXT", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("note", TableInfo.Column("note", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("merchantName", TableInfo.Column("merchantName", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("isRecurring", TableInfo.Column("isRecurring", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("recurringRuleId", TableInfo.Column("recurringRuleId", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("tags", TableInfo.Column("tags", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("hasAttachments", TableInfo.Column("hasAttachments", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("createdAt", TableInfo.Column("createdAt", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactions.put("updatedAt", TableInfo.Column("updatedAt", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTransactions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTransactions.add(TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION",
            listOf("categoryId"), listOf("id")))
        _foreignKeysTransactions.add(TableInfo.ForeignKey("subcategories", "SET NULL", "NO ACTION",
            listOf("subcategoryId"), listOf("id")))
        _foreignKeysTransactions.add(TableInfo.ForeignKey("accounts", "SET NULL", "NO ACTION",
            listOf("accountId"), listOf("id")))
        val _indicesTransactions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTransactions.add(TableInfo.Index("index_transactions_categoryId", false,
            listOf("categoryId"), listOf("ASC")))
        _indicesTransactions.add(TableInfo.Index("index_transactions_subcategoryId", false,
            listOf("subcategoryId"), listOf("ASC")))
        _indicesTransactions.add(TableInfo.Index("index_transactions_accountId", false,
            listOf("accountId"), listOf("ASC")))
        _indicesTransactions.add(TableInfo.Index("index_transactions_date", false, listOf("date"),
            listOf("ASC")))
        _indicesTransactions.add(TableInfo.Index("index_transactions_type", false, listOf("type"),
            listOf("ASC")))
        val _infoTransactions: TableInfo = TableInfo("transactions", _columnsTransactions,
            _foreignKeysTransactions, _indicesTransactions)
        val _existingTransactions: TableInfo = read(connection, "transactions")
        if (!_infoTransactions.equals(_existingTransactions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |transactions(com.expensemanager.app.data.db.entity.TransactionEntity).
              | Expected:
              |""".trimMargin() + _infoTransactions + """
              |
              | Found:
              |""".trimMargin() + _existingTransactions)
        }
        val _columnsCategories: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCategories.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCategories.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCategories.put("iconName", TableInfo.Column("iconName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCategories.put("colorHex", TableInfo.Column("colorHex", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCategories.put("isDefault", TableInfo.Column("isDefault", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCategories.put("sortOrder", TableInfo.Column("sortOrder", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCategories: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCategories: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCategories: TableInfo = TableInfo("categories", _columnsCategories,
            _foreignKeysCategories, _indicesCategories)
        val _existingCategories: TableInfo = read(connection, "categories")
        if (!_infoCategories.equals(_existingCategories)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |categories(com.expensemanager.app.data.db.entity.CategoryEntity).
              | Expected:
              |""".trimMargin() + _infoCategories + """
              |
              | Found:
              |""".trimMargin() + _existingCategories)
        }
        val _columnsSubcategories: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSubcategories.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubcategories.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubcategories.put("parentCategoryId", TableInfo.Column("parentCategoryId",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSubcategories: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysSubcategories.add(TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION",
            listOf("parentCategoryId"), listOf("id")))
        val _indicesSubcategories: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSubcategories.add(TableInfo.Index("index_subcategories_parentCategoryId", false,
            listOf("parentCategoryId"), listOf("ASC")))
        val _infoSubcategories: TableInfo = TableInfo("subcategories", _columnsSubcategories,
            _foreignKeysSubcategories, _indicesSubcategories)
        val _existingSubcategories: TableInfo = read(connection, "subcategories")
        if (!_infoSubcategories.equals(_existingSubcategories)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |subcategories(com.expensemanager.app.data.db.entity.SubcategoryEntity).
              | Expected:
              |""".trimMargin() + _infoSubcategories + """
              |
              | Found:
              |""".trimMargin() + _existingSubcategories)
        }
        val _columnsAccounts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAccounts.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("currency", TableInfo.Column("currency", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("initialBalance", TableInfo.Column("initialBalance", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("iconName", TableInfo.Column("iconName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("colorHex", TableInfo.Column("colorHex", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("isDefault", TableInfo.Column("isDefault", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAccounts.put("sortOrder", TableInfo.Column("sortOrder", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAccounts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAccounts: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoAccounts: TableInfo = TableInfo("accounts", _columnsAccounts, _foreignKeysAccounts,
            _indicesAccounts)
        val _existingAccounts: TableInfo = read(connection, "accounts")
        if (!_infoAccounts.equals(_existingAccounts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |accounts(com.expensemanager.app.data.db.entity.AccountEntity).
              | Expected:
              |""".trimMargin() + _infoAccounts + """
              |
              | Found:
              |""".trimMargin() + _existingAccounts)
        }
        val _columnsTags: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTags.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTags.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTags.put("colorHex", TableInfo.Column("colorHex", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTags: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTags: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTags: TableInfo = TableInfo("tags", _columnsTags, _foreignKeysTags, _indicesTags)
        val _existingTags: TableInfo = read(connection, "tags")
        if (!_infoTags.equals(_existingTags)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tags(com.expensemanager.app.data.db.entity.TagEntity).
              | Expected:
              |""".trimMargin() + _infoTags + """
              |
              | Found:
              |""".trimMargin() + _existingTags)
        }
        val _columnsTransactionTagCrossRef: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTransactionTagCrossRef.put("transactionId", TableInfo.Column("transactionId",
            "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionTagCrossRef.put("tagId", TableInfo.Column("tagId", "INTEGER", true, 2,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTransactionTagCrossRef: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTransactionTagCrossRef.add(TableInfo.ForeignKey("transactions", "CASCADE",
            "NO ACTION", listOf("transactionId"), listOf("id")))
        _foreignKeysTransactionTagCrossRef.add(TableInfo.ForeignKey("tags", "CASCADE", "NO ACTION",
            listOf("tagId"), listOf("id")))
        val _indicesTransactionTagCrossRef: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTransactionTagCrossRef.add(TableInfo.Index("index_transaction_tag_cross_ref_transactionId",
            false, listOf("transactionId"), listOf("ASC")))
        _indicesTransactionTagCrossRef.add(TableInfo.Index("index_transaction_tag_cross_ref_tagId",
            false, listOf("tagId"), listOf("ASC")))
        val _infoTransactionTagCrossRef: TableInfo = TableInfo("transaction_tag_cross_ref",
            _columnsTransactionTagCrossRef, _foreignKeysTransactionTagCrossRef,
            _indicesTransactionTagCrossRef)
        val _existingTransactionTagCrossRef: TableInfo = read(connection,
            "transaction_tag_cross_ref")
        if (!_infoTransactionTagCrossRef.equals(_existingTransactionTagCrossRef)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |transaction_tag_cross_ref(com.expensemanager.app.data.db.entity.TransactionTagCrossRef).
              | Expected:
              |""".trimMargin() + _infoTransactionTagCrossRef + """
              |
              | Found:
              |""".trimMargin() + _existingTransactionTagCrossRef)
        }
        val _columnsBudgets: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBudgets.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("categoryId", TableInfo.Column("categoryId", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("amount", TableInfo.Column("amount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("period", TableInfo.Column("period", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("rolloverEnabled", TableInfo.Column("rolloverEnabled", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("rolloverAmount", TableInfo.Column("rolloverAmount", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("startDate", TableInfo.Column("startDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgets.put("alertThreshold", TableInfo.Column("alertThreshold", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBudgets: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysBudgets.add(TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION",
            listOf("categoryId"), listOf("id")))
        val _indicesBudgets: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesBudgets.add(TableInfo.Index("index_budgets_categoryId", false, listOf("categoryId"),
            listOf("ASC")))
        val _infoBudgets: TableInfo = TableInfo("budgets", _columnsBudgets, _foreignKeysBudgets,
            _indicesBudgets)
        val _existingBudgets: TableInfo = read(connection, "budgets")
        if (!_infoBudgets.equals(_existingBudgets)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |budgets(com.expensemanager.app.data.db.entity.BudgetEntity).
              | Expected:
              |""".trimMargin() + _infoBudgets + """
              |
              | Found:
              |""".trimMargin() + _existingBudgets)
        }
        val _columnsGoals: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsGoals.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("targetAmount", TableInfo.Column("targetAmount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("currentAmount", TableInfo.Column("currentAmount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("targetDate", TableInfo.Column("targetDate", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("iconName", TableInfo.Column("iconName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("colorHex", TableInfo.Column("colorHex", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGoals.put("isCompleted", TableInfo.Column("isCompleted", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysGoals: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesGoals: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoGoals: TableInfo = TableInfo("goals", _columnsGoals, _foreignKeysGoals,
            _indicesGoals)
        val _existingGoals: TableInfo = read(connection, "goals")
        if (!_infoGoals.equals(_existingGoals)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |goals(com.expensemanager.app.data.db.entity.GoalEntity).
              | Expected:
              |""".trimMargin() + _infoGoals + """
              |
              | Found:
              |""".trimMargin() + _existingGoals)
        }
        val _columnsRecurringRules: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRecurringRules.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("templateTransactionId",
            TableInfo.Column("templateTransactionId", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("frequency", TableInfo.Column("frequency", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("interval", TableInfo.Column("interval", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("nextOccurrence", TableInfo.Column("nextOccurrence", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("time", TableInfo.Column("time", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("endDate", TableInfo.Column("endDate", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("amount", TableInfo.Column("amount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("categoryId", TableInfo.Column("categoryId", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("accountId", TableInfo.Column("accountId", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("paymentMethod", TableInfo.Column("paymentMethod", "TEXT", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("note", TableInfo.Column("note", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecurringRules.put("merchantName", TableInfo.Column("merchantName", "TEXT", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRecurringRules: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesRecurringRules: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoRecurringRules: TableInfo = TableInfo("recurring_rules", _columnsRecurringRules,
            _foreignKeysRecurringRules, _indicesRecurringRules)
        val _existingRecurringRules: TableInfo = read(connection, "recurring_rules")
        if (!_infoRecurringRules.equals(_existingRecurringRules)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |recurring_rules(com.expensemanager.app.data.db.entity.RecurringRuleEntity).
              | Expected:
              |""".trimMargin() + _infoRecurringRules + """
              |
              | Found:
              |""".trimMargin() + _existingRecurringRules)
        }
        val _columnsReminders: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReminders.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("description", TableInfo.Column("description", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("dueDate", TableInfo.Column("dueDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("leadTimeDays", TableInfo.Column("leadTimeDays", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("recurringRuleId", TableInfo.Column("recurringRuleId", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReminders.put("isNotified", TableInfo.Column("isNotified", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReminders: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesReminders: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoReminders: TableInfo = TableInfo("reminders", _columnsReminders,
            _foreignKeysReminders, _indicesReminders)
        val _existingReminders: TableInfo = read(connection, "reminders")
        if (!_infoReminders.equals(_existingReminders)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reminders(com.expensemanager.app.data.db.entity.ReminderEntity).
              | Expected:
              |""".trimMargin() + _infoReminders + """
              |
              | Found:
              |""".trimMargin() + _existingReminders)
        }
        val _columnsLending: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLending.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("personName", TableInfo.Column("personName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("amount", TableInfo.Column("amount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("date", TableInfo.Column("date", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("note", TableInfo.Column("note", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLending.put("repaidAmount", TableInfo.Column("repaidAmount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLending: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLending: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLending: TableInfo = TableInfo("lending", _columnsLending, _foreignKeysLending,
            _indicesLending)
        val _existingLending: TableInfo = read(connection, "lending")
        if (!_infoLending.equals(_existingLending)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |lending(com.expensemanager.app.data.db.entity.LendingEntity).
              | Expected:
              |""".trimMargin() + _infoLending + """
              |
              | Found:
              |""".trimMargin() + _existingLending)
        }
        val _columnsSplitExpenses: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSplitExpenses.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSplitExpenses.put("transactionId", TableInfo.Column("transactionId", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSplitExpenses.put("personName", TableInfo.Column("personName", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSplitExpenses.put("shareAmount", TableInfo.Column("shareAmount", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSplitExpenses.put("isSettled", TableInfo.Column("isSettled", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSplitExpenses: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysSplitExpenses.add(TableInfo.ForeignKey("transactions", "CASCADE", "NO ACTION",
            listOf("transactionId"), listOf("id")))
        val _indicesSplitExpenses: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSplitExpenses.add(TableInfo.Index("index_split_expenses_transactionId", false,
            listOf("transactionId"), listOf("ASC")))
        val _infoSplitExpenses: TableInfo = TableInfo("split_expenses", _columnsSplitExpenses,
            _foreignKeysSplitExpenses, _indicesSplitExpenses)
        val _existingSplitExpenses: TableInfo = read(connection, "split_expenses")
        if (!_infoSplitExpenses.equals(_existingSplitExpenses)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |split_expenses(com.expensemanager.app.data.db.entity.SplitExpenseEntity).
              | Expected:
              |""".trimMargin() + _infoSplitExpenses + """
              |
              | Found:
              |""".trimMargin() + _existingSplitExpenses)
        }
        val _columnsQuickAddShortcuts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsQuickAddShortcuts.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("label", TableInfo.Column("label", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("amount", TableInfo.Column("amount", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("categoryId", TableInfo.Column("categoryId", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("accountId", TableInfo.Column("accountId", "INTEGER", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("iconName", TableInfo.Column("iconName", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuickAddShortcuts.put("sortOrder", TableInfo.Column("sortOrder", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysQuickAddShortcuts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysQuickAddShortcuts.add(TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION",
            listOf("categoryId"), listOf("id")))
        _foreignKeysQuickAddShortcuts.add(TableInfo.ForeignKey("accounts", "SET NULL", "NO ACTION",
            listOf("accountId"), listOf("id")))
        val _indicesQuickAddShortcuts: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesQuickAddShortcuts.add(TableInfo.Index("index_quick_add_shortcuts_categoryId", false,
            listOf("categoryId"), listOf("ASC")))
        _indicesQuickAddShortcuts.add(TableInfo.Index("index_quick_add_shortcuts_accountId", false,
            listOf("accountId"), listOf("ASC")))
        val _infoQuickAddShortcuts: TableInfo = TableInfo("quick_add_shortcuts",
            _columnsQuickAddShortcuts, _foreignKeysQuickAddShortcuts, _indicesQuickAddShortcuts)
        val _existingQuickAddShortcuts: TableInfo = read(connection, "quick_add_shortcuts")
        if (!_infoQuickAddShortcuts.equals(_existingQuickAddShortcuts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |quick_add_shortcuts(com.expensemanager.app.data.db.entity.QuickAddShortcutEntity).
              | Expected:
              |""".trimMargin() + _infoQuickAddShortcuts + """
              |
              | Found:
              |""".trimMargin() + _existingQuickAddShortcuts)
        }
        val _columnsSavedFilters: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSavedFilters.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedFilters.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedFilters.put("filterJson", TableInfo.Column("filterJson", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSavedFilters: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSavedFilters: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSavedFilters: TableInfo = TableInfo("saved_filters", _columnsSavedFilters,
            _foreignKeysSavedFilters, _indicesSavedFilters)
        val _existingSavedFilters: TableInfo = read(connection, "saved_filters")
        if (!_infoSavedFilters.equals(_existingSavedFilters)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |saved_filters(com.expensemanager.app.data.db.entity.SavedFilterEntity).
              | Expected:
              |""".trimMargin() + _infoSavedFilters + """
              |
              | Found:
              |""".trimMargin() + _existingSavedFilters)
        }
        val _columnsCurrencies: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCurrencies.put("code", TableInfo.Column("code", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCurrencies.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCurrencies.put("symbol", TableInfo.Column("symbol", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCurrencies.put("conversionRateToBase", TableInfo.Column("conversionRateToBase",
            "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCurrencies: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCurrencies: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCurrencies: TableInfo = TableInfo("currencies", _columnsCurrencies,
            _foreignKeysCurrencies, _indicesCurrencies)
        val _existingCurrencies: TableInfo = read(connection, "currencies")
        if (!_infoCurrencies.equals(_existingCurrencies)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |currencies(com.expensemanager.app.data.db.entity.CurrencyEntity).
              | Expected:
              |""".trimMargin() + _infoCurrencies + """
              |
              | Found:
              |""".trimMargin() + _existingCurrencies)
        }
        val _columnsAttachments: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAttachments.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("transactionId", TableInfo.Column("transactionId", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("fileName", TableInfo.Column("fileName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("filePath", TableInfo.Column("filePath", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("mimeType", TableInfo.Column("mimeType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("thumbnailPath", TableInfo.Column("thumbnailPath", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAttachments.put("createdAt", TableInfo.Column("createdAt", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAttachments: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAttachments.add(TableInfo.ForeignKey("transactions", "CASCADE", "NO ACTION",
            listOf("transactionId"), listOf("id")))
        val _indicesAttachments: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAttachments.add(TableInfo.Index("index_attachments_transactionId", false,
            listOf("transactionId"), listOf("ASC")))
        val _infoAttachments: TableInfo = TableInfo("attachments", _columnsAttachments,
            _foreignKeysAttachments, _indicesAttachments)
        val _existingAttachments: TableInfo = read(connection, "attachments")
        if (!_infoAttachments.equals(_existingAttachments)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |attachments(com.expensemanager.app.data.db.entity.AttachmentEntity).
              | Expected:
              |""".trimMargin() + _infoAttachments + """
              |
              | Found:
              |""".trimMargin() + _existingAttachments)
        }
        val _columnsSettings: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSettings.put("key", TableInfo.Column("key", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSettings.put("value", TableInfo.Column("value", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSettings: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSettings: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSettings: TableInfo = TableInfo("settings", _columnsSettings, _foreignKeysSettings,
            _indicesSettings)
        val _existingSettings: TableInfo = read(connection, "settings")
        if (!_infoSettings.equals(_existingSettings)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |settings(com.expensemanager.app.data.db.entity.SettingsEntity).
              | Expected:
              |""".trimMargin() + _infoSettings + """
              |
              | Found:
              |""".trimMargin() + _existingSettings)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "transactions", "categories",
        "subcategories", "accounts", "tags", "transaction_tag_cross_ref", "budgets", "goals",
        "recurring_rules", "reminders", "lending", "split_expenses", "quick_add_shortcuts",
        "saved_filters", "currencies", "attachments", "settings")
  }

  public override fun clearAllTables() {
    super.performClear(true, "transactions", "categories", "subcategories", "accounts", "tags",
        "transaction_tag_cross_ref", "budgets", "goals", "recurring_rules", "reminders", "lending",
        "split_expenses", "quick_add_shortcuts", "saved_filters", "currencies", "attachments",
        "settings")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(TransactionDao::class, TransactionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(CategoryDao::class, CategoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SubcategoryDao::class, SubcategoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AccountDao::class, AccountDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TagDao::class, TagDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(BudgetDao::class, BudgetDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(GoalDao::class, GoalDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(RecurringRuleDao::class, RecurringRuleDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ReminderDao::class, ReminderDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LendingDao::class, LendingDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SplitExpenseDao::class, SplitExpenseDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(QuickAddShortcutDao::class,
        QuickAddShortcutDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SavedFilterDao::class, SavedFilterDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(CurrencyDao::class, CurrencyDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AttachmentDao::class, AttachmentDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SettingsDao::class, SettingsDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun transactionDao(): TransactionDao = _transactionDao.value

  public override fun categoryDao(): CategoryDao = _categoryDao.value

  public override fun subcategoryDao(): SubcategoryDao = _subcategoryDao.value

  public override fun accountDao(): AccountDao = _accountDao.value

  public override fun tagDao(): TagDao = _tagDao.value

  public override fun budgetDao(): BudgetDao = _budgetDao.value

  public override fun goalDao(): GoalDao = _goalDao.value

  public override fun recurringRuleDao(): RecurringRuleDao = _recurringRuleDao.value

  public override fun reminderDao(): ReminderDao = _reminderDao.value

  public override fun lendingDao(): LendingDao = _lendingDao.value

  public override fun splitExpenseDao(): SplitExpenseDao = _splitExpenseDao.value

  public override fun quickAddShortcutDao(): QuickAddShortcutDao = _quickAddShortcutDao.value

  public override fun savedFilterDao(): SavedFilterDao = _savedFilterDao.value

  public override fun currencyDao(): CurrencyDao = _currencyDao.value

  public override fun attachmentDao(): AttachmentDao = _attachmentDao.value

  public override fun settingsDao(): SettingsDao = _settingsDao.value
}
