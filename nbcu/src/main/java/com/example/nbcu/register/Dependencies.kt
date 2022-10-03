package com.example.nbcu.register

import kotlin.reflect.KClass

/**
 * A dependency needed in the object graph
 */
data class Dependencies <T: Any> (
    val className: String?,
    val classRef: T
)