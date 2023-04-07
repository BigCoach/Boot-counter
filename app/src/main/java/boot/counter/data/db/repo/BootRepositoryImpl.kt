package boot.counter.data.db.repo

import android.content.Context
import boot.counter.data.db.AppDatabase
import boot.counter.data.db.dao.BootEventDao
import boot.counter.domain.repo.BootRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BootRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : BootRepository {

    val db = AppDatabase.getInstance(context)
    val dao: BootEventDao by lazy { db.bootEventDao() }

    override suspend fun getBootEventsTimeStamps(): List<Long> {
       return dao.getAllBootEvents().map { it.bootTime }
    }

}