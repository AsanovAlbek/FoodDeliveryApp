package albek.petprojects.fooddeliveryapp.features.menu.domain.repository

import albek.petprojects.fooddeliveryapp.features.menu.data.model.MealDto

interface IRepository {
    suspend fun categories(): List<String>
    suspend fun areas(): List<String>
    suspend fun mealsByCategory(category: String): List<MealDto>
    suspend fun mealsByArea(area: String): List<MealDto>
}