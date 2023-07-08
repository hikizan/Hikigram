package com.hikizan.hikigram.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikizan.hikigram.domain.membership.AuthUseCase
import com.hikizan.hikigram.utils.proceed
import kotlinx.coroutines.launch

class ProfileViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _loginNameResult: MutableLiveData<String> = MutableLiveData()
    val loginNameResult: LiveData<String> get() = _loginNameResult

    fun getLoginName() = viewModelScope.launch {
        proceed(_loginNameResult) {
            authUseCase.getLoginName()
        }
    }

    fun logoutUser() = viewModelScope.launch {
        authUseCase.logoutUser()
    }
}