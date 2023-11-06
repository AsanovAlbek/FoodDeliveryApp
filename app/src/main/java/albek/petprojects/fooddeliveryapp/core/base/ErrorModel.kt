package albek.petprojects.fooddeliveryapp.core.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ErrorModel(
    @StringRes val message: Int = 0,
    @DrawableRes val image: Int = 0
)
