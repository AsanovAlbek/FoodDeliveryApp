package albek.petprojects.fooddeliveryapp.network.retrofit

import albek.petprojects.fooddeliveryapp.network.model.AreaResponse
import albek.petprojects.fooddeliveryapp.network.model.CategoryResponse
import albek.petprojects.fooddeliveryapp.network.model.MealResponse
import albek.petprojects.fooddeliveryapp.network.model.RemoteArea
import albek.petprojects.fooddeliveryapp.network.model.RemoteCategory
import albek.petprojects.fooddeliveryapp.network.model.RemoteMeal
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodDeliveryApiService {
    @GET("filter.php")
    suspend fun mealsByCategory(@Query("c") category: String): MealResponse

    @GET("filter.php")
    suspend fun mealByArea(@Query("a") area: String): MealResponse

    @GET("list.php?a=list")
    suspend fun areas(): AreaResponse

    @GET("list.php?c=list")
    suspend fun categories(): CategoryResponse
}