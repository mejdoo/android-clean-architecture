package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.domain.model.User


/**
 * Map a [UserEntity] to a [User] instance when data is moving between
 * this layer and the Domain layer
 */
open class UserEntityMapper :
    Mapper<UserEntity, User> {


    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapToDomain(entity: UserEntity): User =
        User(
            entity.id,
            entity.name,
            entity.email,
            entity.phone,
            entity.website
        )

    /**
     * Map a list of [UserEntity] to a  list of [User]
     */
    override fun mapListToDomain(list: List<UserEntity>): List<User> = list.map { mapToDomain(it) }


    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapFromDomain(model: User): UserEntity =
        UserEntity(
            model.id,
            model.name,
            model.email,
            model.phone,
            model.website
        )
}