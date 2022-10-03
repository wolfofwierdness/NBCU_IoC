package com.example.nbcu.register

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

/**
 * Creates registration of valid dependencies for the Object Graph
 */
class NBCURegister<T: Any>: Registration{
    private var queueOfDependencies: MutableSet<Dependencies<T>> = HashSet()

    /**
     * Register possible dependencies to build the Object graph
     */
    override fun includeDependency(dependency: KClass<*>) {
        val currentDependency = createDependency(dependency)

        queueOfDependencies.add(currentDependency)

    }

    override fun <T: Any> findRegistration(dependency: KClass<*>): T {
        return getRepresentation( dependency ).first as T
    }

    override fun isDependencyCreated(dependency: KClass<*>): Boolean {
        val isFounded = getRepresentation( dependency )

        return isFounded.second
    }

    private fun getRepresentation(dependency: KClass<*>): Pair<Dependencies<T>?, Boolean>{
        return queueOfDependencies.find {
                it.className == dependency.simpleName
            }?.toPair() ?: notFounded()
    }

    private fun notFounded(): Pair<Dependencies<T>?, Boolean> = null to false

    private fun createDependency(current: KClass<*>): Dependencies<T>{
        val currentInstance = current::primaryConstructor.get()
        println(currentInstance)
        val objectCreated = currentInstance!!.call() as T
        println(objectCreated)
        // todo if a class has multiple parameters in the constructor, identify those first
        // todo to avoid circular references.

        return Dependencies(current.simpleName, objectCreated)
    }
}

private fun <T:Any> Dependencies<T>.toPair(): Pair<Dependencies<T>?, Boolean> {
    return this to true
}

interface Registration{
    fun includeDependency(dependency: KClass<*>)
    fun <T: Any> findRegistration(dependency: KClass<*>): T
    fun isDependencyCreated(dependency: KClass<*>): Boolean
}
