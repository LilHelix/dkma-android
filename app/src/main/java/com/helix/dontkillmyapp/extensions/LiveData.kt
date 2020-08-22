package com.helix.dontkillmyapp.extensions

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