package com.expensemanager.app.data.export

import android.content.Context
import android.net.Uri
import com.expensemanager.app.data.db.entity.*
import com.expensemanager.app.data.repository.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CsvImporter(
    private val context: Context,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) {
    data class ImportResult(
        val imported: Int,
        val skipped: Int,
        val errors: List<String>
    )

    suspend fun import(uri: Uri): ImportResult {
        val categories = categoryRepository.getAllCategories().associateBy { it.name.lowercase() }
        val accounts = accountRepository.getAll().associateBy { it.name.lowercase() }
        val dateFormats = listOf(
            DateTimeFormatter.ofPattern("dd MMM yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
        )

        var imported = 0
        var skipped = 0
        val errors = mutableListOf<String>()

        context.contentResolver.openInputStream(uri)?.use { stream ->
            BufferedReader(InputStreamReader(stream)).use { reader ->
                val header = reader.readLine() ?: return ImportResult(0, 0, listOf("Empty file"))
                val headerCols = header.split(",").map { it.trim().lowercase() }

                val dateIdx = headerCols.indexOfFirst { it in listOf("date", "transaction date", "txn date") }
                val amountIdx = headerCols.indexOfFirst { it in listOf("amount", "debit amount", "credit amount", "withdrawal", "deposit") }
                val descIdx = headerCols.indexOfFirst { it in listOf("description", "narration", "note", "remarks", "particular", "merchant") }
                val typeIdx = headerCols.indexOfFirst { it in listOf("type", "cr/dr", "transaction type") }

                if (dateIdx == -1 || amountIdx == -1) {
                    return ImportResult(0, 0, listOf("Could not find date and amount columns"))
                }

                var lineNum = 1
                for (line in reader.lineSequence()) {
                    lineNum++
                    try {
                        val cols = parseCsvLine(line)
                        if (cols.size <= maxOf(dateIdx, amountIdx)) {
                            skipped++
                            continue
                        }

                        val dateStr = cols.getOrNull(dateIdx)?.trim() ?: ""
                        val amountStr = cols.getOrNull(amountIdx)?.trim()?.replace(",", "") ?: ""
                        val desc = cols.getOrNull(descIdx)?.trim() ?: ""
                        val typeStr = cols.getOrNull(typeIdx)?.trim()?.uppercase() ?: ""

                        val date = tryParseDates(dateStr, dateFormats)
                        if (date == null) {
                            skipped++
                            errors.add("Line $lineNum: Invalid date '$dateStr'")
                            continue
                        }

                        val amount = amountStr.toBigDecimalOrNull()
                        if (amount == null || amount == BigDecimal.ZERO) {
                            skipped++
                            continue
                        }

                        val type = when {
                            typeStr in listOf("INCOME", "CR", "CREDIT", "DEPOSIT") -> TransactionType.INCOME
                            typeStr in listOf("EXPENSE", "DR", "DEBIT", "WITHDRAWAL") -> TransactionType.EXPENSE
                            amount < BigDecimal.ZERO -> TransactionType.EXPENSE
                            else -> TransactionType.EXPENSE
                        }

                        val transaction = TransactionEntity(
                            amount = amount.abs(),
                            type = type,
                            date = date,
                            note = desc.ifBlank { null }
                        )
                        transactionRepository.insert(transaction)
                        imported++
                    } catch (e: Exception) {
                        skipped++
                        errors.add("Line $lineNum: ${e.message}")
                    }
                }
            }
        }

        return ImportResult(imported, skipped, errors)
    }

    private fun tryParseDates(dateStr: String, formats: List<DateTimeFormatter>): LocalDate? {
        for (fmt in formats) {
            try {
                return LocalDate.parse(dateStr, fmt)
            } catch (_: DateTimeParseException) {}
        }
        return null
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    result.add(current.toString())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        result.add(current.toString())
        return result
    }
}
