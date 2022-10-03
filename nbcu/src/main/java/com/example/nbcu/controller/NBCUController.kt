package com.example.nbcu.controller

import com.example.nbcu.provider.NCUResolve
import com.example.nbcu.provider.Resolve
import com.example.nbcu.register.NBCURegister
import com.example.nbcu.register.Registration
import com.example.nbcu.util.Exceptions.NotEmptyDependencyException
import com.example.nbcu.util.Exceptions.NotFoundedDependencyException
import kotlin.reflect.KClass

/**
 * Init class of Register/Provider
 */
class NBCUController: Controller {

    // IoC Container Framework
    /*
    Register
    Resolve
    Dispose
     */
    private val assembler: Resolve by lazy{
        NCUResolve(register)
    }

    private val register: Registration by lazy{
        NBCURegister<Any>()
    }

    override fun <T : Any> addObjectToGraph(dependency: KClass<T>) {
        register.includeDependency(dependency)
    }

    @Throws(exceptionClasses = [NotEmptyDependencyException::class, NotFoundedDependencyException::class])
    override fun <T : Any> getDependency(dependencyName: KClass<T>): T{
        return assembler.getDependency(dependencyName)
    }
}

interface Controller{
    fun <T: Any> addObjectToGraph(dependency: KClass<T>)
    fun <T: Any> getDependency(dependencyName: KClass<T>): T
}