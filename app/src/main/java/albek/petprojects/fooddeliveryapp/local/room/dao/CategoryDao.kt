package albek.petprojects.fooddeliveryapp.local.room.dao

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_CATEGORY
import albek.petprojects.fooddeliveryapp.local.room.entity.CategoryEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CategoryDao {
    @Upsert
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM $TABLE_CATEGORY")
    suspend fun deleteCategories()

    @Query("SELECT * FROM $TABLE_CATEGORY")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_CATEGORY)")
    suspend fun isCategoriesExist(): Boolean
}