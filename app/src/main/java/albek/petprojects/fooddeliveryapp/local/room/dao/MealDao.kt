package albek.petprojects.fooddeliveryapp.local.room.dao

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_MEAL
import albek.petprojects.fooddeliveryapp.local.room.entity.MealEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MealDao {
    @Upsert
    suspend fun addMeals(meals: List<MealEntity>)

    @Query("DELETE FROM $TABLE_MEAL")
    suspend fun deleteMeals()

    @Query("SELECT * FROM $TABLE_MEAL WHERE area = :area")
    suspend fun mealsByArea(area: String): List<MealEntity>

    @Query("SELECT * FROM $TABLE_MEAL")
    suspend fun allMeals(): List<MealEntity>

    @Query("SELECT * FROM $TABLE_MEAL WHERE category = :category")
    suspend fun mealsByCategory(category: String): List<MealEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_MEAL)")
    suspend fun isMealsExists(): Boolean
}