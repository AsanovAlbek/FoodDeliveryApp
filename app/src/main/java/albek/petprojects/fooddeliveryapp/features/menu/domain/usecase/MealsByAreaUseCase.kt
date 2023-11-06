package albek.petprojects.fooddeliveryapp.features.menu.domain.usecase

import albek.petprojects.fooddeliveryapp.core.base.SourceNotFoundException
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import albek.petprojects.fooddeliveryapp.features.menu.toDomain
import android.util.Log
import javax.inject.Inject

class MealsByAreaUseCase @Inject constructor(
    private val repository: IRepository,
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(area: String, isNetworkAvailable: Boolean) =
        if (isNetworkAvailable) {
            val mealsByArea = repository.mealsByArea(area = area)
            cacheRepository.cache(meals = mealsByArea)
            mealsByArea.map { it.toDomain() }
        } else if (cacheRepository.hasCachedData()) {
            cacheRepository.mealsByArea(area = area).map { it.toDomain() }
        } else {
            throw SourceNotFoundException("Source not found!")
        }
}