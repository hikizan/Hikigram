package com.hikizan.hikigram.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikizan.hikigram.domain.membership.AuthUseCase
import com.hikizan.hikigram.utils.proceed
import kotlinx.coroutines.launch

class MainActivityViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _loginStateResult: MutableLiveData<Boolean> = MutableLiveData()
    val loginStateResult: LiveData<Boolean> get() = _loginStateResult

    private val _loginNameResult: MutableLiveData<String> = MutableLiveData()
    val loginNameResult: LiveData<String> get() = _loginNameResult

    fun getLoginState() = viewModelScope.launch {
        proceed(_loginStateResult) {
            authUseCase.getLoginState()
        }
    }

    fun getLoginName() = viewModelScope.launch {
        proceed(_loginNameResult) {
            authUseCase.getLoginName()
        }
    }

    fun logoutUser() = viewModelScope.launch {
        authUseCase.logoutUser()
    }
}