package boot.counter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import boot.counter.data.db.dao.BootEventDao
import boot.counter.data.db.model.BootEvent

@Database(entities = [BootEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bootEventDao(): BootEventDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance = it }
            }
        }
    }
}