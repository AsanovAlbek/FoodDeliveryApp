package albek.petprojects.fooddeliveryapp.features.menu.data.model

data class MealDto(
    val id: Int = 0,
    val name: String = "",
    val instruction: String = "",
    val imageUrl: String = "",
    val area: String = "",
    val category: String = ""
)
