package io.aircall.android.di.builder

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.aircall.android.presentation.ui.detail.KotlinPublicRepositoryDetailViewModel
import io.aircall.android.presentation.ui.repositories.TopKotlinPublicRepositoriesViewModel

@Module
abstract class ViewModelsBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(TopKotlinPublicRepositoriesViewModel::class)
    abstract fun bindMainViewModel(topKotlinPublicRepositoriesViewModel: TopKotlinPublicRepositoriesViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(KotlinPublicRepositoryDetailViewModel::class)
    abstract fun bindDetailViewModel(kotlinPublicRepositoryDetailViewModel: KotlinPublicRepositoryDetailViewModel): ViewModel
}