package albek.petprojects.fooddeliveryapp.features.menu.domain.repository

import albek.petprojects.fooddeliveryapp.features.menu.data.model.MealDto

interface CacheRepository: IRepository {
    suspend fun cache(
        meals: List<MealDto>? = null,
        categories: List<String>? = null,
        areas: List<String>? = null
    )

    suspend fun hasCachedData(): Boolean
}