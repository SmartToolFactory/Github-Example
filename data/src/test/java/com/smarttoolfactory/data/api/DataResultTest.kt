package com.smarttoolfactory.data.api

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DataResultTest {


    @Test
    fun success() {

        // GIVEN
        val data = "test"

        // WHEN
        val dataResult = DataResult.Success<String>(data)

        // THEN
        assertEquals(dataResult.data, data)
        assertEquals(dataResult.status, Status.SUCCESS)

    }

    @Test
    fun exception() {

        // GIVEN
        val exception = Exception("test")

        // WHEN
        val dataResult = DataResult.Error<String>(exception)

        // THEN
        assertThat(dataResult.error?.message, `is`("test"))
        assertEquals(dataResult.error, exception)
        assertEquals(dataResult.status, Status.ERROR)

    }

    @Test
    fun loading() {

        val dataResult = DataResult.Loading<String>()

        assertEquals(dataResult.javaClass, DataResult.Loading::class.java)
        assertEquals(dataResult.status, Status.LOADING)


    }

}