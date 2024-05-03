package com.application.tripapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.application.tripapp.R
import com.application.tripapp.usecase.LoadPictureUseCase
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate

@HiltWorker
class WorkerPicture @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
) : CoroutineWorker(context, params) {

    init {
        createNotificationChannel()
    }

    override suspend fun doWork(): Result {
        return try {

            showNotification()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannelId = "picture_of_the_day_channel"
        val notificationChannel = NotificationChannel(
            notificationChannelId,
            context.getString(R.string.picture_of_the_day),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun showNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannelId = "picture_of_the_day_channel"

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(context.getString(R.string.new_picture_of_the_day))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(0, notification)
    }
}