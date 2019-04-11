package com.mejdoo.clean.presentation.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 */
interface Mapper<D, P> {

    fun mapFromDomain(entity: D): P

    fun mapListFromDomain(list: List<D>): List<P>

}