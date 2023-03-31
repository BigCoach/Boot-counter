package boot.counter.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import boot.counter.data.db.model.BootEvent

@Dao
interface BootEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bootEvent: BootEvent)

    @Query("SELECT * FROM boot_events ORDER BY id ASC")
    suspend fun getAllBootEvents(): List<BootEvent>
}