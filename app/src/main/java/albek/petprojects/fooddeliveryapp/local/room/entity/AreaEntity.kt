package albek.petprojects.fooddeliveryapp.local.room.entity

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_AREA
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_AREA)
data class AreaEntity(
    @PrimaryKey
    val name: String = ""
)
