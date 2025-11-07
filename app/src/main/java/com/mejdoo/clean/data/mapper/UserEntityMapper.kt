package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.domain.model.User

// UserMappers.kt

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    website = website
)

fun User.toData() = UserEntity(
    id = id,
    name = name,
    email = email,
    phone = phone,
    website = website
)

fun List<UserEntity>.toDomainList() = map { it.toDomain() }
fun List<User>.toDataList() = map { it.toData() }