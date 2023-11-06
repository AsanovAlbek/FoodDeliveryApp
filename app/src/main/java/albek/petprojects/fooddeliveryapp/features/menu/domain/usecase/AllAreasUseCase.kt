package albek.petprojects.fooddeliveryapp.features.menu.domain.usecase

import albek.petprojects.fooddeliveryapp.core.base.SourceNotFoundException
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import android.util.Log
import javax.inject.Inject

class AllAreasUseCase @Inject constructor(
    private val repository: IRepository,
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(isNetworkAvailable: Boolean) =
        if (isNetworkAvailable) {
            val areas = repository.areas()
            cacheRepository.cache(areas = areas)
            areas
        } else if (cacheRepository.hasCachedData()) {
            cacheRepository.areas()
        } else {
            throw SourceNotFoundException("Source not found!")
        }
}