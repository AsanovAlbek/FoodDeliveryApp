package albek.petprojects.fooddeliveryapp.network.retrofit

import albek.petprojects.fooddeliveryapp.network.NetworkConst
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodDeliveryClient {
    fun createClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConst.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}