package albek.petprojects.fooddeliveryapp.features.menu.presentation

import albek.petprojects.fooddeliveryapp.features.menu.domain.model.Meal

data class MenuState(
    val meals: List<Meal> = emptyList(),
    val categories: List<String> = emptyList(),
    val areas: List<String> = emptyList(),
    val chosenCategory: String = "",
    val chosenArea: String = "",
    val contentState: ContentState = ContentState.LOADING
)

enum class ContentState {
    LOADING, CONTENT, ERROR, EMPTY
}