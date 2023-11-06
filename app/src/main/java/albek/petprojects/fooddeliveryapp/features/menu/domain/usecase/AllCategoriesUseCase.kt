package albek.petprojects.fooddeliveryapp.features.menu.domain.usecase

import albek.petprojects.fooddeliveryapp.core.base.SourceNotFoundException
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.CacheRepository
import albek.petprojects.fooddeliveryapp.features.menu.domain.repository.IRepository
import albek.petprojects.fooddeliveryapp.features.menu.toDomain
import android.util.Log
import javax.inject.Inject

class AllCategoriesUseCase @Inject constructor(
    private val repository: IRepository,
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(isNetworkAvailable: Boolean) =
        if (isNetworkAvailable) {
            val categories = repository.categories()
            cacheRepository.cache(categories = categories)
            categories
        } else if(cacheRepository.hasCachedData()) {
            cacheRepository.categories()
        }
        else {
            throw SourceNotFoundException("Source not found!")
        }
}