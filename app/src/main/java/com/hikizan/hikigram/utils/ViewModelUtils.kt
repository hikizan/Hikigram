package com.hikizan.hikigram.utils

import androidx.lifecycle.MutableLiveData
import com.hikizan.hikigram.domain.reuseable.State
import kotlinx.coroutines.flow.Flow

suspend fun <U> proceed(outputLiveData: MutableLiveData<State<U>>, block: suspend () -> Flow<State<U>>) {
    block.invoke()
        .collect {
            outputLiveData.value = it
        }
}