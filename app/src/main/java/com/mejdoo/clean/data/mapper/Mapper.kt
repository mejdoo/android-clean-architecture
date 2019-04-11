package com.mejdoo.clean.data.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 */
interface Mapper<E, D> {

    fun mapToDomain(entity: E): D

    fun mapListToDomain(list: List<E>): List<D>

    fun mapFromDomain(model: D): E


}