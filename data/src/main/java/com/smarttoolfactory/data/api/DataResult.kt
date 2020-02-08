package com.smarttoolfactory.data.api

/**
 * Class that retrieves the result from network or database
 *
 * This class is helpful to send Throwable type to ui when different type of exceptions require
 *  different actions
 */
sealed class DataResult<out T> {

    class Loading<out T> : DataResult<T>()
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error<out T>(val error: Throwable) : DataResult<T>()

}