package com.smarttoolfactory.githubexample.model

import com.smarttoolfactory.data.api.Status


class ViewState<T>(
    val status: Status,
    val data: List<T>? = null,
    val error: Throwable? = null
) {
    fun getRepos() = data ?: mutableListOf()

    fun isLoading() = status == Status.LOADING

    fun getErrorMessage() = error?.message

    fun shouldShowErrorMessage() = error != null
}