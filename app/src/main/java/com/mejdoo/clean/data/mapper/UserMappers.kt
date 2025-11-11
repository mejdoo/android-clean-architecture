package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.domain.model.User

// UserMappers.kt

fun UserEntity.toUser() =
    User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        website = website,
    )

fun User.toUserEntity() =
    UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        website = website,
    )

fun List<UserEntity>.toUserList() = map { it.toUser() }

fun List<User>.toUserEntityList() = map { it.toUserEntity() }
