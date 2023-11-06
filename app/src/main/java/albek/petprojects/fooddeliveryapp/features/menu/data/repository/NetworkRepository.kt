package albek.petprojects.fooddeliveryapp.features.menu.data.repository

import albek.petprojects.fooddeliveryapp.features.menu.data.model.MealDto
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import albek.petprojects.fooddeliveryapp.features.menu.toDto
import albek.petprojects.fooddeliveryapp.network.retrofit.FoodDeliveryApiService

class NetworkRepository(
    private val apiService: FoodDeliveryApiService
) : IRepository {
    override suspend fun categories(): List<String> =
        apiService.categories().remoteCategories.map { remoteCategory -> remoteCategory.toDto() }

    override suspend fun areas(): List<String> =
        apiService.areas().remoteAreas.map { remoteArea -> remoteArea.toDto() }

    override suspend fun mealsByCategory(category: String): List<MealDto> =
        apiService.mealsByCategory(category = category).remoteMeals.map { remoteMeal -> remoteMeal.toDto() }

    override suspend fun mealsByArea(area: String): List<MealDto> =
        apiService.mealByArea(area = area).remoteMeals.map { remoteMeal -> remoteMeal.toDto() }
}