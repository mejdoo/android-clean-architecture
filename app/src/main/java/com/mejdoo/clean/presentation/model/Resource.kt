package com.mejdoo.clean.presentation.model

data class Resource<T>(
        val status: ResourceStatus,
        val data: Any?,
        val message: String?
)