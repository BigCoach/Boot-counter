package boot.counter.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boot_events")
data class BootEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "boot_time") val bootTime: Long
)