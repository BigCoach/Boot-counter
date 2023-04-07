package boot.counter.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import boot.counter.R
import boot.counter.data.db.AppDatabase
import boot.counter.data.db.model.BootEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Alarm received")
        GlobalScope.launch {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "default_channel_id"
                val channelName = "Default Channel"
                val channel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            val bootEvents = getBootEventsFromDatabase(context)
            Log.d(TAG, "bootEvents: $bootEvents")

            // Create the notification text based on the boot events
            val notificationText = when (bootEvents.size) {
                0 -> "No boots detected"
                1 -> "The boot was detected with the timestamp = ${bootEvents[0].bootTime}"
                else -> {
                    val lastBootTime = bootEvents[bootEvents.size - 1].bootTime
                    val preLastBootTime = bootEvents[bootEvents.size - 2].bootTime
                    val timeDelta = lastBootTime - preLastBootTime
                    "Last boots time delta = $timeDelta"
                }
            }
            Log.d(TAG, "notificationText: $bootEvents")


            val notificationBuilder = NotificationCompat.Builder(context, "default_channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Boot events")
                .setContentText(notificationText)
                .setAutoCancel(true)

            withContext(Dispatchers.Main){
                Log.d(TAG, "posting notification..")
                notificationManager.notify(0, notificationBuilder.build())
            }
        }
    }

    private suspend fun getBootEventsFromDatabase(context: Context): List<BootEvent> {
        val db = AppDatabase.getInstance(context)
        return db.bootEventDao().getAllBootEvents()
    }

    companion object{
        const val TAG = "NotificationReceiver"
    }
}