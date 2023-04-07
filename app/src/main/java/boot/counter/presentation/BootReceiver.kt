package boot.counter.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import boot.counter.data.db.AppDatabase
import boot.counter.data.db.model.BootEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val bootTime = System.currentTimeMillis()
            val bootEvent = BootEvent(bootTime = bootTime)

            val db = context?.let { AppDatabase.getInstance(it) }
            GlobalScope.launch {
                db?.bootEventDao()?.insert(bootEvent)
            }

            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val notificationIntent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val intervalMillis = 1 * 60 * 1000 // 15 minutes in milliseconds
            val triggerAtMillis = System.currentTimeMillis() + intervalMillis
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                intervalMillis.toLong(),
                pendingIntent
            )
        }
    }
}