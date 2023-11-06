package albek.petprojects.fooddeliveryapp.network.model

import com.google.gson.annotations.SerializedName

data class RemoteCategory(
    @SerializedName(value = "strCategory")
    val name: String = ""
)
