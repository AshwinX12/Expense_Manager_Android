package com.expensemanager.app.data.export

import android.content.Context
import android.net.Uri
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.repository.CategoryRepository
import com.expensemanager.app.data.repository.AccountRepository
import com.expensemanager.app.util.formatDisplay
import java.io.OutputStreamWriter

class CsvExporter(
    private val context: Context,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) {
    suspend fun export(transactions: List<TransactionEntity>, uri: Uri) {
        val categories = categoryRepository.getAllCategories().associateBy { it.id }
        val accounts = accountRepository.getAll().associateBy { it.id }

        context.contentResolver.openOutputStream(uri)?.use { stream ->
            OutputStreamWriter(stream).use { writer ->
                // Header
                writer.write("Date,Type,Amount,Category,Account,PaymentMethod,Merchant,Note,Tags\n")

                transactions.forEach { tx ->
                    val line = listOf(
                        tx.date.formatDisplay(),
                        tx.type.name,
                        tx.amount.toPlainString(),
                        tx.categoryId?.let { categories[it]?.name } ?: "",
                        tx.accountId?.let { accounts[it]?.name } ?: "",
                        tx.paymentMethod?.name ?: "",
                        escapeCsv(tx.merchantName ?: ""),
                        escapeCsv(tx.note ?: ""),
                        tx.tags?.joinToString(";") ?: ""
                    ).joinToString(",")
                    writer.write("$line\n")
                }
            }
        }
    }

    private fun escapeCsv(value: String): String {
        return if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            "\"${value.replace("\"", "\"\"")}\""
        } else value
    }
}
