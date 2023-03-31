package boot.counter.data.db.repo

import boot.counter.data.db.dao.BootEventDao
import boot.counter.domain.repo.BootRepository
import javax.inject.Inject

class BootRepositoryImpl @Inject constructor(
    bootEventDao: BootEventDao
) : BootRepository {

    override suspend fun getBootEventsTimeStamps(): List<Long> {
        TODO("Not yet implemented")
    }

}