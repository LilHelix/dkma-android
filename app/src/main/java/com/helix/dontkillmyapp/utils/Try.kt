package com.helix.dontkillmyapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

// region Try
suspend fun <T> attempt(block: suspend () -> T): Try<T> {
    return try {
        val result = block.invoke()
        Success(result)
    } catch (any: Throwable) {
        Failure(any)
    }
}

sealed class Try<T> {

    val isSuccess: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun fold(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit = {}) {
        when (this) {
            is Success -> onSuccess.invoke(result)
            is Failure -> onError.invoke(throwable)
        }
    }

    fun doOnSuccess(action: (T) -> Unit) {
        if (this is Success) {
            action.invoke(result)
        }
    }
}

class Success<T>(val result: T) : Try<T>()
class Failure<T>(val throwable: Throwable) : Try<T>()
//endregion

// region Operations with progress
fun <T> progressive(block: suspend () -> Try<T>): Flow<LongOperation<T>> {
    return flow {
        emit(InProgress<T>())
        val result = Finished(block.invoke())
        emit(result)
    }
}

sealed class LongOperation<T> {

    fun fold(onStarted: () -> Unit, onFinished: (Try<T>) -> Unit) {
        when (this) {
            is InProgress<T> -> onStarted.invoke()
            is Finished<T> -> onFinished.invoke(result)
        }
    }

    fun fold(onStarted: () -> Unit, onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        when (this) {
            is InProgress<T> -> onStarted.invoke()
            is Finished<T> -> result.fold(onSuccess, onError)
        }
    }

    fun doOnSuccess(action: (T) -> Unit) {
        if (this is Finished) result.doOnSuccess(action)
    }
}

class InProgress<T> : LongOperation<T>()
class Finished<T>(val result: Try<T>) : LongOperation<T>()
//endregion