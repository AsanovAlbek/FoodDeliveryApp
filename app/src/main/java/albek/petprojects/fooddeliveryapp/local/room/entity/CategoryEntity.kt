package albek.petprojects.fooddeliveryapp.local.room.entity

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_CATEGORY
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_CATEGORY)
data class CategoryEntity(
    @PrimaryKey
    val name: String = ""
)
