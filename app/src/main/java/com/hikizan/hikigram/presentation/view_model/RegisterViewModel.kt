package com.hikizan.hikigram.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikizan.hikigram.domain.membership.AuthUseCase
import com.hikizan.hikigram.domain.membership.model.request.RegisterParam
import com.hikizan.hikigram.domain.membership.model.response.Register
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.utils.proceed
import kotlinx.coroutines.launch

class RegisterViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _registerUserResult: MutableLiveData<State<Register>> = MutableLiveData()
    val registerUserResult: LiveData<State<Register>> get() = _registerUserResult

    fun registerUser(
        name: String, email: String, password: String
    ) = viewModelScope.launch {
        proceed(_registerUserResult) {
            authUseCase.registerUser(
                registerParam = RegisterParam(name, email, password)
            )
        }
    }
}