package com.sonder.boredapp.data.di

import com.sonder.boredapp.data.repository.ActivityRepository
import com.sonder.boredapp.data.repository.ActivityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindActivityRepository(
        activityRepository: ActivityRepositoryImpl
    ): ActivityRepository
}
