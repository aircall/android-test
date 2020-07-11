package io.aircall.android.presentation.ui.repositories

import io.aircall.android.domain.model.KotlinPublicRepository

interface TopKotlinPublicRepositoriesCallback {
    fun onKotlinPublicRepositorySelected(kotlinPublicRepository: KotlinPublicRepository)
}