package com.expensemanager.app.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.expensemanager.app.MainActivity
import com.expensemanager.app.R
import com.expensemanager.app.util.Constants

object NotificationHelper {

    fun createChannels(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channels = listOf(
            NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_BUDGET,
                "Budget Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when you're approaching or exceeding your budget"
            },
            NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_REMINDERS,
                "Bill Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for upcoming bill payments"
            },
            NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_SUMMARIES,
                "Weekly Summaries",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Weekly spending summaries"
            },
            NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_UNUSUAL,
                "Unusual Spending",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts for unusually high spending"
            }
        )

        channels.forEach { manager.createNotificationChannel(it) }
    }

    fun showBudgetAlert(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        showNotification(context, Constants.NOTIFICATION_CHANNEL_BUDGET, title, message, notificationId)
    }

    fun showReminderNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        showNotification(context, Constants.NOTIFICATION_CHANNEL_REMINDERS, title, message, notificationId)
    }

    fun showWeeklySummary(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        showNotification(context, Constants.NOTIFICATION_CHANNEL_SUMMARIES, title, message, notificationId)
    }

    fun showUnusualSpending(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        showNotification(context, Constants.NOTIFICATION_CHANNEL_UNUSUAL, title, message, notificationId)
    }

    private fun showNotification(
        context: Context,
        channelId: String,
        title: String,
        message: String,
        notificationId: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}
