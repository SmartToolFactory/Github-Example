package com.smarttoolfactory.domain.base


abstract class BaseUseCase {

    /**
     * This method is required to dispose RxJava Observables
     */
    abstract fun dispose()
}