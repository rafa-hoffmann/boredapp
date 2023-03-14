package com.sonder.boredapp.database

import com.sonder.boredapp.database.dao.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: BoredDatabase,
    ): ActivityDao = database.activityDao()
}
