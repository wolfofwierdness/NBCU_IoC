package com.example.nbcu.controller

import com.example.nbcu.util.Exceptions.NotEmptyDependencyException
import com.example.nbcu.util.Exceptions.NotFoundedDependencyException
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class NBCUControllerTest {

    private lateinit var classUnderTest: NBCUController

    @Before
    fun init() {
        classUnderTest = NBCUController()
    }

    @Test
    fun `add non recorded Object To Graph throws exception`() {
        assertThrows(NotFoundedDependencyException::class.java) {
            classUnderTest.getDependency(String::class)
        }
    }

    @Test
    fun `add recorded Object To Graph not throws exception`() {
        classUnderTest.addObjectToGraph(String::class)
        classUnderTest.getDependency(String::class)
    }

    @Test
    fun `add recorded Object To Graph get different exception throws error`() {
        classUnderTest.addObjectToGraph(String::class)
        assertThrows(NotFoundedDependencyException::class.java) {
            classUnderTest.getDependency(Int::class)
        }
    }

    @Test
    fun `get Dependency is same as Stored`() {
        val expected = String(CharArray(1){ 'Z' })

        classUnderTest.addObjectToGraph(expected::class)

        val actual = classUnderTest.getDependency(expected::class)

        assert(expected::class.isInstance(actual))
    }
}