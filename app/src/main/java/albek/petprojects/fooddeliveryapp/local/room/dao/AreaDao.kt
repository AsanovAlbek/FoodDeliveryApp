package albek.petprojects.fooddeliveryapp.local.room.dao

import albek.petprojects.fooddeliveryapp.local.LocalConst.TABLE_AREA
import albek.petprojects.fooddeliveryapp.local.room.entity.AreaEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AreaDao {
    @Upsert
    suspend fun addAreas(areas: List<AreaEntity>)

    @Query("DELETE FROM $TABLE_AREA")
    suspend fun deleteAreas()

    @Query("SELECT * FROM $TABLE_AREA")
    suspend fun getAreas(): List<AreaEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_AREA)")
    suspend fun isAreaExist(): Boolean
}