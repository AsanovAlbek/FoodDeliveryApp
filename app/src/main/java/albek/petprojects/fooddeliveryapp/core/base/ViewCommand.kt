package albek.petprojects.fooddeliveryapp.core.base

import androidx.annotation.StringRes

sealed interface ViewCommand
data class ShowSnackBar(
    @StringRes val messageId: Int? = null,
    val message: String? = null
    ): ViewCommand