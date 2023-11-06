package albek.petprojects.fooddeliveryapp.features.menu.di

import albek.petprojects.fooddeliveryapp.features.menu.data.repository.LocalRepository
import albek.petprojects.fooddeliveryapp.features.menu.data.repository.NetworkRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import albek.petprojects.fooddeliveryapp.local.room.dao.AreaDao
import albek.petprojects.fooddeliveryapp.local.room.dao.CategoryDao
import albek.petprojects.fooddeliveryapp.local.room.dao.MealDao
import albek.petprojects.fooddeliveryapp.network.retrofit.FoodDeliveryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MenuModule {
    @Provides
    fun provideRepository(apiService: FoodDeliveryApiService): IRepository =
        NetworkRepository(apiService)

    @Provides
    fun provideCacheRepository(
        mealDao: MealDao,
        categoryDao: CategoryDao,
        areaDao: AreaDao
    ): CacheRepository = LocalRepository(
        mealDao = mealDao,
        categoryDao = categoryDao,
        areaDao = areaDao
    )
}