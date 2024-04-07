package com.example.totalhealth

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat

class NotificationHelper(private val context: Context) {
    fun showNotification(context: Context, title: String, content: String) {
        val intent = Intent(context, AddFoodItemActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val bubbleMetadata = NotificationCompat.BubbleMetadata.Builder()
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground))
            .setIntent(pendingIntent).build()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use appropriate icon
            .setContentTitle(title).setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setBubbleMetadata(bubbleMetadata)

        with(NotificationManagerCompat.from(context)) {
            if (!isNotificationPermissionGranted(context)) {
                // TODO: Consider calling ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult
                //   (int requestCode, String[] permissions, int[] grantResults)
                // to handle the case where the user grants the permission.
                // See the documentation for ActivityCompat#requestPermissions
                // for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "health_tracker_channel"
        const val REQUEST_CODE_POST_NOTIFICATIONS_PERMISSION = 2001

        fun isNotificationPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }

        fun requestNotificationPermission(context: Context) {
            Log.d("NotificationHelper", "Requesting notification permission")
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFICATIONS_PERMISSION
            )
        }


    }
}