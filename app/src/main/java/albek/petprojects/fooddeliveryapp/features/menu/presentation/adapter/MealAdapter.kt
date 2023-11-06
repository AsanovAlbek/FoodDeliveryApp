package albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter

import albek.petprojects.fooddeliveryapp.databinding.MealItemBinding
import albek.petprojects.fooddeliveryapp.features.menu.domain.model.Meal
import albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.diff.MealDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealAdapter(
    private val meals: MutableList<Meal> = mutableListOf(),
    private val click: (Meal) -> Unit = {}
): RecyclerView.Adapter<MealAdapter.MealHolder>() {
    inner class MealHolder(
        private val mealItemBinding: MealItemBinding
    ): RecyclerView.ViewHolder(mealItemBinding.root) {
        fun bindItem(meal: Meal) {
            mealItemBinding.run {
                mealName.text = meal.name
                // api не предоставляет какого либо описания или ингредиентов для
                // блюд, если бы оно это делало, эта строчка была бы валидной
                // p.s. в деталках блюд есть такое, но подгружать каждую отдельно только ради этого
                // не целесообразно
                //mealDescription.text = meal.instruction
                Glide.with(mealImage).load(meal.imageUrl).into(mealImage)
                root.setOnClickListener { click(meal) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder =
        MealHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        holder.bindItem(meals[position])
    }

    fun update(newMeals: List<Meal>) {
        val callback = MealDiffCallback(oldList = meals, newList = newMeals)
        val diff = DiffUtil.calculateDiff(callback)
        meals.clear()
        meals.addAll(newMeals)
        diff.dispatchUpdatesTo(this)
    }
}