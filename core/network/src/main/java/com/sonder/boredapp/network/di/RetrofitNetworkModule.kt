package com.sonder.boredapp.network.di

import com.sonder.boredapp.network.di.retrofit.RetrofitBoredNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitNetworkModule {
    @Binds
    fun RetrofitBoredNetwork.binds(): BoredNetworkDataSource
}
