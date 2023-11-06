package albek.petprojects.fooddeliveryapp.local.di

import albek.petprojects.fooddeliveryapp.local.room.dao.AreaDao
import albek.petprojects.fooddeliveryapp.local.room.dao.CategoryDao
import albek.petprojects.fooddeliveryapp.local.room.dao.MealDao
import albek.petprojects.fooddeliveryapp.local.room.database.FoodDeliveryDatabase
import android.content.Context
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        FoodDeliveryDatabase.createDatabase(context = appContext)

    @Provides
    fun provideCategoryDao(database: FoodDeliveryDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideAreaDao(database: FoodDeliveryDatabase): AreaDao = database.areaDao()

    @Provides
    fun provideMealDao(database: FoodDeliveryDatabase): MealDao = database.mealDao()
}