package boot.counter.domain

import boot.counter.domain.repo.BootRepository
import javax.inject.Inject

class GetBootEventsUseCase @Inject constructor(
    private val bootRepository: BootRepository
) {

    suspend fun execute(): List<Long> {
        return bootRepository.getBootEventsTimeStamps()
    }

}