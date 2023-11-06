package albek.petprojects.fooddeliveryapp.local.room.database

import albek.petprojects.fooddeliveryapp.local.LocalConst
import albek.petprojects.fooddeliveryapp.local.room.dao.AreaDao
import albek.petprojects.fooddeliveryapp.local.room.dao.CategoryDao
import albek.petprojects.fooddeliveryapp.local.room.dao.MealDao
import albek.petprojects.fooddeliveryapp.local.room.entity.AreaEntity
import albek.petprojects.fooddeliveryapp.local.room.entity.CategoryEntity
import albek.petprojects.fooddeliveryapp.local.room.entity.MealEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AreaEntity::class, CategoryEntity::class, MealEntity::class],
    exportSchema = false,
    version = 1
)
abstract class FoodDeliveryDatabase: RoomDatabase() {

abstract fun areaDao(): AreaDao
abstract fun categoryDao(): CategoryDao
abstract fun mealDao(): MealDao

    companion object {
        fun createDatabase(context: Context) =
            Room.databaseBuilder(context, FoodDeliveryDatabase::class.java, LocalConst.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}