package albek.petprojects.fooddeliveryapp.local.room.entity

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_MEAL
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_MEAL)
data class MealEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val instruction: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val area: String = ""
)
