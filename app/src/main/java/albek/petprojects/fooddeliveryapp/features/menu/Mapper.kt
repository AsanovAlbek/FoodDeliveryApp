package albek.petprojects.fooddeliveryapp.features.menu

import albek.petprojects.fooddeliveryapp.features.menu.data.model.MealDto
import albek.petprojects.fooddeliveryapp.features.menu.domain.model.Meal
import albek.petprojects.fooddeliveryapp.local.room.entity.AreaEntity
import albek.petprojects.fooddeliveryapp.local.room.entity.CategoryEntity
import albek.petprojects.fooddeliveryapp.local.room.entity.MealEntity
import albek.petprojects.fooddeliveryapp.network.model.RemoteArea
import albek.petprojects.fooddeliveryapp.network.model.RemoteCategory
import albek.petprojects.fooddeliveryapp.network.model.RemoteMeal

// From database
fun MealEntity.toDto() = MealDto(
    id = id,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    area = area,
    category = category
)
fun CategoryEntity.toDto() = name
fun AreaEntity.toDto(): String = name

// From network

fun RemoteMeal.toDto() = MealDto(
    id = id,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    area = area,
    category = category
)
fun RemoteArea.toDto(): String = name
fun RemoteCategory.toDto() = name

// To database
fun MealDto.toEntity() = MealEntity(
    id = id,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    area = area,
    category = category
)
fun String.toEntity() = CategoryEntity(name = this)

// Data to Domain
fun MealDto.toDomain() = Meal(
    id = id,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    area = area,
    category = category
)

// Domain to Data
fun Meal.toDto() = MealDto(
    id = id,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    area = area,
    category = category
)