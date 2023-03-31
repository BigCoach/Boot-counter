package boot.counter.domain.repo

import boot.counter.data.db.model.BootEvent

interface BootRepository {
    suspend fun getBootEventsTimeStamps(): List<Long>
}