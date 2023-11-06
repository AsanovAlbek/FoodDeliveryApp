package albek.petprojects.fooddeliveryapp.features.menu.domain.usecase

import albek.petprojects.fooddeliveryapp.core.base.SourceNotFoundException
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import albek.petprojects.fooddeliveryapp.features.menu.toDomain
import android.util.Log
import javax.inject.Inject

class MealsByCategoryUseCase @Inject constructor(
    private val repository: IRepository,
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(category: String, isNetworkAvailable: Boolean) =
        if (isNetworkAvailable) {
            val mealsByCategory = repository.mealsByCategory(category = category)
            cacheRepository.cache(meals = mealsByCategory)
            mealsByCategory.map { it.toDomain() }
        } else if (cacheRepository.hasCachedData()) {
            cacheRepository.mealsByCategory(category = category).map { it.toDomain() }
        } else {
            throw SourceNotFoundException("Source not found!")
        }
}