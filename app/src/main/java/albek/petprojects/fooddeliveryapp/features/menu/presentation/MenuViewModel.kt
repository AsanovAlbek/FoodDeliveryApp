package albek.petprojects.fooddeliveryapp.features.menu.presentation

import albek.petprojects.fooddeliveryapp.R
import albek.petprojects.fooddeliveryapp.core.base.BaseViewModel
import albek.petprojects.fooddeliveryapp.core.base.ShowSnackBar
import albek.petprojects.fooddeliveryapp.features.menu.domain.usecase.AllAreasUseCase
import albek.petprojects.fooddeliveryapp.features.menu.domain.usecase.AllCategoriesUseCase
import albek.petprojects.fooddeliveryapp.features.menu.domain.usecase.MealsByAreaUseCase
import albek.petprojects.fooddeliveryapp.features.menu.domain.usecase.MealsByCategoryUseCase
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val areasUseCase: AllAreasUseCase,
    private val categoriesUseCase: AllCategoriesUseCase,
    private val mealsByAreaUseCase: MealsByAreaUseCase,
    private val mealsByCategoryUseCase: MealsByCategoryUseCase
) : BaseViewModel() {
    private val mutableState = MutableStateFlow(MenuState(contentState = ContentState.LOADING))
    val state get() = mutableState.asStateFlow()

    fun initial(isNetworkAvailable: Boolean) = runOnIo {
        val categories = categoriesUseCase(isNetworkAvailable)
        val areas = areasUseCase(isNetworkAvailable)
        val mealsByCategory = mealsByCategoryUseCase(
            category = categories.first(),
            isNetworkAvailable = isNetworkAvailable
        )
        val mealsByArea =
            mealsByAreaUseCase(area = areas.first(), isNetworkAvailable = isNetworkAvailable)
        val meals = mealsByCategory.intersect(mealsByArea.toSet()).toList()
        val contentState = if (meals.isEmpty()) ContentState.EMPTY else ContentState.CONTENT
        mutableState.update { prevValue ->
            prevValue.copy(
                categories = categories,
                areas = areas,
                meals = meals,
                contentState = contentState,
                chosenArea = areas.first(),
                chosenCategory = categories.first()
            )
        }
        runOnUi {
            if (!isNetworkAvailable) {
                executeCommand(ShowSnackBar(messageId = R.string.offline_mode_message))
            }
        }
    }

    private fun updateMeal(isNetworkAvailable: Boolean) = runOnIo {
        mutableState.update { prevValue ->
            val meals = mealsByCategoryUseCase(
                category = prevValue.chosenCategory,
                isNetworkAvailable = isNetworkAvailable
            ).intersect(
                mealsByAreaUseCase(
                    area = prevValue.chosenArea,
                    isNetworkAvailable = isNetworkAvailable
                ).toSet()
            )
            val contentState = if (meals.isEmpty()) ContentState.EMPTY else ContentState.CONTENT
            prevValue.copy(
                meals = meals.toList(),
                contentState = contentState
            )
        }
    }

    fun updateArea(area: String = "", isNetworkAvailable: Boolean) = runOnUi {
        mutableState.update { prevValue ->
            prevValue.copy(
                chosenArea = area.ifEmpty { prevValue.areas.first() },
                contentState = ContentState.CONTENT
            )
        }
        updateMeal(isNetworkAvailable)
    }

    fun updateCategory(category: String = "", isNetworkAvailable: Boolean) = runOnUi {
        mutableState.update { prevValue ->
            prevValue.copy(
                chosenCategory = category.ifEmpty { prevValue.categories.first() },
                contentState = ContentState.CONTENT
            )
        }
        updateMeal(isNetworkAvailable)
    }

    fun clickOnMeal(message: String) {
        executeCommand(ShowSnackBar(message = message))
    }

    fun clickOnBoard(message: String) {
        executeCommand(ShowSnackBar(message = message))
    }

    fun showError() = runOnUi {
        mutableState.update { prevValue ->
            prevValue.copy(contentState = ContentState.ERROR)
        }
    }
}