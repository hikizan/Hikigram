package com.hikizan.hikigram.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikizan.hikigram.domain.membership.AuthUseCase
import com.hikizan.hikigram.domain.membership.model.request.LoginParam
import com.hikizan.hikigram.domain.membership.model.response.Login
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.utils.proceed
import kotlinx.coroutines.launch

class LoginViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _loginUserResult: MutableLiveData<State<Login>> = MutableLiveData()
    val loginUserResult: LiveData<State<Login>> get() = _loginUserResult

    private val _loginStateResult: MutableLiveData<Boolean> = MutableLiveData()
    val loginStateResult: LiveData<Boolean> get() = _loginStateResult

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        proceed(_loginUserResult) {
            authUseCase.loginUser(
                loginParam = LoginParam(email, password)
            )
        }
    }

    fun getLoginState() = viewModelScope.launch {
        proceed(_loginStateResult) {
            authUseCase.getLoginState()
        }
    }
}