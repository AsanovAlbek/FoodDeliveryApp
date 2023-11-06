package albek.petprojects.fooddeliveryapp.network.model

import com.google.gson.annotations.SerializedName

data class RemoteMeal(
    @SerializedName("idMeal")
    val id: Int = 0,
    @SerializedName("strMeal")
    val name: String = "",
    @SerializedName("strInstructions")
    val instruction: String = "",
    @SerializedName("strMealThumb")
    val imageUrl: String = "",
    @SerializedName("strCategory")
    val category: String = "",
    @SerializedName("strArea")
    val area: String = ""
)
