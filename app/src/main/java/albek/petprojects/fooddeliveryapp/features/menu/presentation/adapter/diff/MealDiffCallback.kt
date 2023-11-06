package albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.diff

import albek.petprojects.fooddeliveryapp.features.menu.domain.model.Meal
import androidx.recyclerview.widget.DiffUtil

class MealDiffCallback(
    private val oldList: List<Meal>,
    private val newList: List<Meal>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}