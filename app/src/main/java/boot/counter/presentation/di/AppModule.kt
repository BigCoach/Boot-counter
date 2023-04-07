package boot.counter.presentation.di

import boot.counter.data.db.repo.BootRepositoryImpl
import boot.counter.domain.repo.BootRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindBootRepository(bootRepositoryImpl: BootRepositoryImpl): BootRepository

}