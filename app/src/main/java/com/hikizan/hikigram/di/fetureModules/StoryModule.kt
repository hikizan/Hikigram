package com.hikizan.hikigram.di.fetureModules

import com.hikizan.hikigram.data.story.ImplStoryRepository
import com.hikizan.hikigram.data.story.RemoteStoryDataSource
import com.hikizan.hikigram.domain.story.StoryInteractor
import com.hikizan.hikigram.domain.story.StoryUseCase
import com.hikizan.hikigram.domain.story.repository.StoryRepository
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storyRepositoryModule = module {
    single { RemoteStoryDataSource(get()) }
    single<StoryRepository> { ImplStoryRepository(get(), get(), get()) }
}

val storyUseCaseModule = module {
    factory<StoryUseCase> { StoryInteractor(get()) }
}

val storyViewModelModule = module {
    viewModel { StoryViewModel(get()) }
}