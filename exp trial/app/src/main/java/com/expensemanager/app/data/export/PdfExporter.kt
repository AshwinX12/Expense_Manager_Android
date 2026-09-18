package com.expensemanager.app.data.export

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.repository.CategoryRepository
import com.expensemanager.app.util.formatCurrency
import com.expensemanager.app.util.formatDisplay
import java.math.BigDecimal

class PdfExporter(
    private val context: Context,
    private val categoryRepository: CategoryRepository
) {
    suspend fun export(
        transactions: List<TransactionEntity>,
        uri: Uri,
        title: String = "Expense Report"
    ) {
        val categories = categoryRepository.getAllCategories().associateBy { it.id }
        val document = PdfDocument()

        val pageWidth = 595  // A4 width in points
        val pageHeight = 842 // A4 height in points
        val margin = 40f
        val lineHeight = 18f

        val titlePaint = Paint().apply {
            color = Color.parseColor("#1A1A2E")
            textSize = 22f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val headerPaint = Paint().apply {
            color = Color.parseColor("#6C63FF")
            textSize = 12f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val bodyPaint = Paint().apply {
            color = Color.parseColor("#333333")
            textSize = 10f
            isAntiAlias = true
        }
        val linePaint = Paint().apply {
            color = Color.parseColor("#E0E0EE")
            strokeWidth = 1f
        }

        var pageNum = 1
        var currentPage = createPage(document, pageWidth, pageHeight, pageNum)
        var canvas = currentPage.canvas
        var yPos = margin + 30f

        // Title
        canvas.drawText(title, margin, yPos, titlePaint)
        yPos += 35f

        // Summary
        val totalExpense = transactions.filter { it.type.name == "EXPENSE" }.sumOf { it.amount }
        val totalIncome = transactions.filter { it.type.name == "INCOME" }.sumOf { it.amount }
        canvas.drawText("Total Expenses: ${totalExpense.formatCurrency()}", margin, yPos, headerPaint)
        yPos += lineHeight
        canvas.drawText("Total Income: ${totalIncome.formatCurrency()}", margin, yPos, headerPaint)
        yPos += lineHeight
        canvas.drawText("Transactions: ${transactions.size}", margin, yPos, headerPaint)
        yPos += lineHeight * 2

        // Table header
        val cols = floatArrayOf(margin, margin + 80, margin + 140, margin + 240, margin + 350, margin + 440)
        val headers = listOf("Date", "Type", "Category", "Merchant", "Amount", "Note")
        canvas.drawLine(margin, yPos, pageWidth - margin, yPos, linePaint)
        yPos += lineHeight
        headers.forEachIndexed { i, h ->
            canvas.drawText(h, cols[i], yPos, headerPaint)
        }
        yPos += 5f
        canvas.drawLine(margin, yPos, pageWidth - margin, yPos, linePaint)
        yPos += lineHeight

        // Rows
        for (tx in transactions) {
            if (yPos > pageHeight - margin - 30) {
                document.finishPage(currentPage)
                pageNum++
                currentPage = createPage(document, pageWidth, pageHeight, pageNum)
                canvas = currentPage.canvas
                yPos = margin + 20f
            }

            canvas.drawText(tx.date.formatDisplay(), cols[0], yPos, bodyPaint)
            canvas.drawText(tx.type.name.take(7), cols[1], yPos, bodyPaint)
            canvas.drawText(
                (tx.categoryId?.let { categories[it]?.name } ?: "-").take(14),
                cols[2], yPos, bodyPaint
            )
            canvas.drawText((tx.merchantName ?: "-").take(14), cols[3], yPos, bodyPaint)
            canvas.drawText(tx.amount.formatCurrency(), cols[4], yPos, bodyPaint)
            canvas.drawText((tx.note ?: "").take(14), cols[5], yPos, bodyPaint)
            yPos += lineHeight
        }

        document.finishPage(currentPage)

        context.contentResolver.openOutputStream(uri)?.use { stream ->
            document.writeTo(stream)
        }
        document.close()
    }

    private fun createPage(doc: PdfDocument, width: Int, height: Int, num: Int): PdfDocument.Page {
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, num).create()
        return doc.startPage(pageInfo)
    }
}
