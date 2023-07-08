package com.hikizan.hikigram.di.fetureModules

import com.hikizan.hikigram.data.membership.ImplAuthRepository
import com.hikizan.hikigram.data.membership.RemoteAuthDataSource
import com.hikizan.hikigram.domain.membership.AuthInteractor
import com.hikizan.hikigram.domain.membership.repository.AuthRepository
import com.hikizan.hikigram.domain.membership.AuthUseCase
import com.hikizan.hikigram.presentation.view_model.LoginViewModel
import com.hikizan.hikigram.presentation.view_model.ProfileViewModel
import com.hikizan.hikigram.presentation.view_model.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authRepositoryModule = module {
    single { RemoteAuthDataSource(get()) }
    single<AuthRepository> { ImplAuthRepository(get(), get()) }
}

val authUseCaseModule = module {
    factory<AuthUseCase> { AuthInteractor(get()) }
}

val authViewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}