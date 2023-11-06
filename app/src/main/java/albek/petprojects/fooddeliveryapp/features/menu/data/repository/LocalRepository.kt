package albek.petprojects.fooddeliveryapp.features.menu.data.repository

import albek.petprojects.fooddeliveryapp.features.menu.data.model.MealDto
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.toDto
import albek.petprojects.fooddeliveryapp.features.menu.toEntity
import albek.petprojects.fooddeliveryapp.local.room.dao.AreaDao
import albek.petprojects.fooddeliveryapp.local.room.dao.CategoryDao
import albek.petprojects.fooddeliveryapp.local.room.dao.MealDao
import albek.petprojects.fooddeliveryapp.local.room.entity.AreaEntity

class LocalRepository(
    private val categoryDao: CategoryDao,
    private val mealDao: MealDao,
    private val areaDao: AreaDao
): CacheRepository {
    override suspend fun categories(): List<String> =
        categoryDao.getCategories().map { categoryEntity -> categoryEntity.toDto() }

    override suspend fun areas(): List<String> =
        areaDao.getAreas().map { areaEntity -> areaEntity.toDto() }

    override suspend fun mealsByCategory(category: String): List<MealDto> =
        mealDao.allMeals().map { it.toDto() }

    override suspend fun mealsByArea(area: String): List<MealDto> =
        mealDao.allMeals().map { it.toDto() }

    override suspend fun cache(
        meals: List<MealDto>?,
        categories: List<String>?,
        areas: List<String>?
    ) {
        meals?.let { mealDao.addMeals(it.map { meal -> meal.toEntity() }) }
        categories?.let { categoryDao.addCategories(it.map { category -> category.toEntity() }) }
        areas?.let { areaDao.addAreas(it.map { area -> AreaEntity(area) }) }
    }

    override suspend fun hasCachedData() =
        areaDao.isAreaExist() && categoryDao.isCategoriesExist() && mealDao.isMealsExists()
}