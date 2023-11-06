package albek.petprojects.fooddeliveryapp.network

import albek.petprojects.fooddeliveryapp.network.retrofit.FoodDeliveryApiService
import albek.petprojects.fooddeliveryapp.network.retrofit.FoodDeliveryClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideClient(): Retrofit = FoodDeliveryClient.createClient()

    @Provides
    fun provideApi(retrofit: Retrofit): FoodDeliveryApiService =
        retrofit.create(FoodDeliveryApiService::class.java)
}