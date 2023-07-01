package com.hikizan.hikigram.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

suspend fun <U> proceed(outputLiveData: MutableLiveData<U>, block: suspend () -> Flow<U>) {
    block.invoke()
        .collect {
            outputLiveData.value = it
        }
}