package com.smarttoolfactory.data.api

import com.smarttoolfactory.data.api.Status.*

/**
 * Class that retrieves the result from network or database
 *
 * This class is helpful to send Throwable type to ui when different type of exceptions require
 *  different actions
 */
sealed class DataResult<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    class Success<T>(data: T) : DataResult<T>(SUCCESS, data)
    class Loading<T> : DataResult<T>(LOADING)
    class Error<T>(error: Throwable) : DataResult<T>(ERROR, error = error)

}

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
