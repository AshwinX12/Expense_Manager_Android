package com.expensemanager.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.RemoteViews
import androidx.compose.ui.graphics.toArgb
import com.expensemanager.app.MainActivity
import com.expensemanager.app.R
import com.expensemanager.app.data.db.AppDatabase
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.AppCurrency
import com.expensemanager.app.util.formatCurrency
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

private data class WidgetPalette(
    val background: Int,
    val textPrimary: Int,
    val textSecondary: Int,
    val accent: Int,
    val expense: Int
)

class ExpenseWidgetProvider : AppWidgetProvider() {

    companion object {
        /**
         * Redraws every placed widget. The system only calls onUpdate every 30 minutes, so
         * without this the widget shows stale (or, right after being placed/moved, empty)
         * figures until the next tick.
         */
        fun refresh(context: Context) {
            val manager = AppWidgetManager.getInstance(context) ?: return
            val ids = manager.getAppWidgetIds(
                android.content.ComponentName(context, ExpenseWidgetProvider::class.java)
            )
            if (ids.isEmpty()) return
            val provider = ExpenseWidgetProvider()
            for (id in ids) provider.updateWidget(context, manager, id)
        }

        private fun paletteFor(styleStr: String, dark: Boolean): WidgetPalette = when (styleStr) {
            "WARM_PAPER" -> if (dark) WidgetPalette(
                background = WarmPaperColors.BackgroundDark.toArgb(),
                textPrimary = WarmPaperColors.TextPrimaryDark.toArgb(),
                textSecondary = WarmPaperColors.TextSecondaryDark.toArgb(),
                accent = WarmPaperColors.AccentDark.toArgb(),
                expense = WarmPaperColors.StatusOver.toArgb()
            ) else WidgetPalette(
                background = WarmPaperColors.SurfaceLight.toArgb(),
                textPrimary = WarmPaperColors.TextPrimaryLight.toArgb(),
                textSecondary = WarmPaperColors.TextSecondaryLight.toArgb(),
                accent = WarmPaperColors.AccentLight.toArgb(),
                expense = WarmPaperColors.StatusOver.toArgb()
            )
            "EDITORIAL" -> if (dark) WidgetPalette(
                background = EditorialColors.BackgroundDark.toArgb(),
                textPrimary = EditorialColors.TextPrimaryDark.toArgb(),
                textSecondary = EditorialColors.TextSecondaryDark.toArgb(),
                accent = EditorialColors.AccentDark.toArgb(),
                expense = EditorialColors.StatusOver.toArgb()
            ) else WidgetPalette(
                background = EditorialColors.BackgroundLight.toArgb(),
                textPrimary = EditorialColors.TextPrimaryLight.toArgb(),
                textSecondary = EditorialColors.TextSecondaryLight.toArgb(),
                accent = EditorialColors.AccentLight.toArgb(),
                expense = EditorialColors.StatusOver.toArgb()
            )
            "MEMPHIS" -> if (dark) WidgetPalette(
                background = MemphisColors.SurfaceDark.toArgb(),
                textPrimary = MemphisColors.TextPrimaryDark.toArgb(),
                textSecondary = MemphisColors.TextSecondaryDark.toArgb(),
                accent = MemphisColors.AccentDark.toArgb(),
                expense = MemphisColors.StatusOver.toArgb()
            ) else WidgetPalette(
                background = MemphisColors.SurfaceLight.toArgb(),
                textPrimary = MemphisColors.TextPrimaryLight.toArgb(),
                textSecondary = MemphisColors.TextSecondaryLight.toArgb(),
                accent = MemphisColors.AccentLight.toArgb(),
                expense = MemphisColors.StatusOver.toArgb()
            )
            "TERMINAL" -> if (dark) WidgetPalette(
                background = TerminalColors.SurfaceDark.toArgb(),
                textPrimary = TerminalColors.TextPrimaryDark.toArgb(),
                textSecondary = TerminalColors.TextSecondaryDark.toArgb(),
                accent = TerminalColors.AccentDark.toArgb(),
                expense = TerminalColors.ErrorDark.toArgb()
            ) else WidgetPalette(
                background = TerminalColors.SurfaceLight.toArgb(),
                textPrimary = TerminalColors.TextPrimaryLight.toArgb(),
                textSecondary = TerminalColors.TextSecondaryLight.toArgb(),
                accent = TerminalColors.AccentLight.toArgb(),
                expense = TerminalColors.ErrorLight.toArgb()
            )
            else -> if (dark) WidgetPalette(
                background = SurfaceDark.toArgb(),
                textPrimary = TextPrimaryDark.toArgb(),
                textSecondary = TextSecondaryDark.toArgb(),
                accent = PrimaryDark.toArgb(),
                expense = ExpenseRed.toArgb()
            ) else WidgetPalette(
                background = SurfaceLight.toArgb(),
                textPrimary = TextPrimaryLight.toArgb(),
                textSecondary = TextSecondaryLight.toArgb(),
                accent = PrimaryLight.toArgb(),
                expense = ExpenseRed.toArgb()
            )
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // Fires when the widget is resized or moved, which re-inflates the (empty) initial layout
    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: android.os.Bundle?
    ) {
        updateWidget(context, appWidgetManager, appWidgetId)
    }

    internal fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_expense_summary)

        // Tapping anywhere opens the app; the + button jumps straight to a new transaction.
        views.setOnClickPendingIntent(R.id.widget_root, launchIntent(context, appWidgetId, openAdd = false))
        views.setOnClickPendingIntent(R.id.widget_add_button, launchIntent(context, appWidgetId, openAdd = true))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val database = AppDatabase.create(context)

                val today = LocalDate.now()
                val monthStart = today.withDayOfMonth(1)
                val monthEnd = today.with(TemporalAdjusters.lastDayOfMonth())

                val todayExpense = database.transactionDao().getTotalExpense(today, today)
                val monthExpense = database.transactionDao().getTotalExpense(monthStart, monthEnd)
                val totalBalance = database.accountDao().getTotalBalance()
                // The widget can update while the app isn't running, so read the chosen
                // currency straight from the database rather than assuming the default.
                val currencyCode = database.settingsDao().getValue("base_currency") ?: "INR"

                val themeModeStr = database.settingsDao().getValue("theme") ?: "SYSTEM"
                val themeStyleStr = database.settingsDao().getValue("theme_style") ?: "CLASSIC"
                val systemNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                val systemDark = systemNightMode == Configuration.UI_MODE_NIGHT_YES
                val dark = when (themeModeStr) {
                    "LIGHT" -> false
                    "DARK" -> true
                    // Terminal defaults to dark even under "System", matching the in-app theme
                    else -> themeStyleStr == "TERMINAL" || systemDark
                }
                val palette = paletteFor(themeStyleStr, dark)

                withContext(Dispatchers.Main) {
                    AppCurrency.set(currencyCode)

                    views.setInt(R.id.widget_root, "setBackgroundColor", palette.background)
                    views.setTextColor(R.id.widget_app_name, palette.accent)
                    views.setTextColor(R.id.widget_date, palette.textSecondary)
                    views.setTextColor(R.id.widget_label_balance, palette.textSecondary)
                    views.setTextColor(R.id.widget_label_today, palette.textSecondary)
                    views.setTextColor(R.id.widget_label_month, palette.textSecondary)
                    views.setTextColor(R.id.widget_balance_amount, palette.textPrimary)
                    views.setTextColor(R.id.widget_month_amount, palette.textPrimary)
                    views.setTextColor(R.id.widget_today_amount, palette.expense)
                    views.setInt(R.id.widget_add_button, "setBackgroundColor", palette.accent)

                    views.setTextViewText(R.id.widget_balance_amount, totalBalance.formatCurrency())
                    views.setTextViewText(R.id.widget_today_amount, todayExpense.formatCurrency())
                    views.setTextViewText(R.id.widget_month_amount, monthExpense.formatCurrency())
                    views.setTextViewText(
                        R.id.widget_date,
                        today.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM"))
                    )
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val zero = java.math.BigDecimal.ZERO.formatCurrency()
                    views.setTextViewText(R.id.widget_balance_amount, zero)
                    views.setTextViewText(R.id.widget_today_amount, zero)
                    views.setTextViewText(R.id.widget_month_amount, zero)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
        }
    }

    private fun launchIntent(context: Context, appWidgetId: Int, openAdd: Boolean): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (openAdd) putExtra(MainActivity.EXTRA_OPEN_ADD_TRANSACTION, true)
        }
        // Distinct request codes so the two actions don't share (and overwrite) one PendingIntent
        val requestCode = appWidgetId * 2 + if (openAdd) 1 else 0
        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
