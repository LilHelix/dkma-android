package com.helix.dontkillmyapp.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helix.dontkillmyapp.utils.LongOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

suspend fun <T> Flow<LongOperation<T>>.collect(liveData: MutableLiveData<LongOperation<T>>) {
    collect { emittedValue ->
        liveData.postValue(emittedValue)
    }
}

val <T> LiveData<T>.requireValue : T
    get() = value ?: throw IllegalArgumentException("Value is not present but was marked as required")