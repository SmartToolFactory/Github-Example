package com.smarttoolfactory.data.utils

import com.smarttoolfactory.data.api.DataResult
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * Extension Functions for RxJava2
 * @author Smart Tool Factory
 *
 */
fun <T> Observable<T>.listen(
    scheduleSubscribe: Scheduler,
    schedulerObserve: Scheduler
): Observable<T> {
    return subscribeOn(scheduleSubscribe)
        .observeOn(schedulerObserve)
}

fun <T> Observable<T>.listenOnMain(): Observable<T> {
    return listen(Schedulers.io(), AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.listenOnIO(): Observable<T> {
    return listen(Schedulers.io(), Schedulers.io())
}


/**
 * Extension method to subscribe an observable on schedulers thread and observe on main.
 *
 */
fun <T> Observable<T>.observeResultOnMain(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
): Disposable {
    return listenOnMain()
        .subscribe(
            {
                onNext(it)
            },
            { throwable ->
                onError?.apply {
                    onError(throwable)
                }
            },
            {
                onComplete?.apply {
                    onComplete()
                }
            })
}


fun <T> Observable<T>.observeResultOnIO(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
): Disposable {
    return listenOnIO()
        .subscribe(
            {
                onNext(it)
            },
            { throwable ->
                onError?.apply {
                    onError(throwable)
                }
            },
            {
                onComplete?.apply {
                    onComplete()
                }
            })
}


fun <T> Observable<T>.convertToResultOnIO(): Observable<DataResult<T>> {
    return this
        .listenOnIO()
        .map<DataResult<T>> { data
            ->
            DataResult.Success(data)
        }
        .onErrorResumeNext { throwable: Throwable ->
            Observable.just(
                DataResult.Error(throwable)
            )
        }
}

fun Disposable.addTo(disposables: CompositeDisposable) {
    disposables.add(this)
}