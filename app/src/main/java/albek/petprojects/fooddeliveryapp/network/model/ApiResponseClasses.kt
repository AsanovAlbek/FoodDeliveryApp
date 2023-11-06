package albek.petprojects.fooddeliveryapp.network.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val remoteMeals: List<RemoteMeal> = emptyList()
)

data class AreaResponse(
    @SerializedName("meals")
    val remoteAreas: List<RemoteArea> = emptyList()
)

data class CategoryResponse(
    @SerializedName("meals")
    val remoteCategories: List<RemoteCategory> = emptyList()
)
