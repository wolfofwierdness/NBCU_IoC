package com.example.nbcu.provider

import com.example.nbcu.register.Dependencies
import com.example.nbcu.register.Registration
import com.example.nbcu.util.Exceptions.NotAnonymousDependencyException
import com.example.nbcu.util.Exceptions.NotEmptyDependencyException
import com.example.nbcu.util.Exceptions.NotFoundedDependencyException
import java.lang.Exception
import kotlin.reflect.KClass

/**
 * Receives dependencies queries to be provider down to clients
 * 2 actions
 * Find a given Name.
 * Return if exists, that dependency.
 */
class NCUResolve(private val registrar: Registration) : Resolve {

    override fun <T: Any>  getDependency(dependency: KClass<*>): T {

        //Simple name could be null if KClass is Anonymous
        if (dependency.simpleName == null) throw NotAnonymousDependencyException("Not supported Anonymous classes")

        val founded = findDependency<T>(dependency)

        return founded?.classRef ?: throw NotFoundedDependencyException("The dependency was not register")
    }

    override fun <T: Any>  findDependency(dependencyName: KClass<*>): Dependencies<T>? {
        println("something $dependencyName")
        if (dependencyName.simpleName == null) throw NotEmptyDependencyException("The dependency name is incorrect: $dependencyName")

        if (!registrar.isDependencyCreated( dependencyName )) throw NotFoundedDependencyException("The dependency doesn't exists: $dependencyName")

        return registrar.findRegistration(dependencyName)
    }
}

interface Resolve {

    fun <T: Any> getDependency(dependency: KClass<*>): T

    fun <T: Any>  findDependency(dependencyName: KClass<*>): Dependencies<T>?
}