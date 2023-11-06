package albek.petprojects.fooddeliveryapp.features.menu.domain.model

data class Meal(
    val id: Int = 0,
    val name: String = "",
    val instruction: String = "",
    val imageUrl: String = "",
    val area: String = "",
    val category: String = ""
)
