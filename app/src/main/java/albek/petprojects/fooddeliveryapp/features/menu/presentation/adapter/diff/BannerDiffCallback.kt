package albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil

// На самом деле избыточно для стрингов, но на деле это был бы отдельный класс, просто в апи такого нет
class BannerDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].compareTo(newList[newItemPosition]) == 0

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}