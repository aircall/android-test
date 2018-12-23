package com.example.alizee.aircalltest.core.di

import javax.inject.Qualifier
import kotlin.annotation.Retention

import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class IoScheduler