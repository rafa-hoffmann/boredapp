package com.sonder.boredapp.network.di

import com.sonder.boredapp.network.di.model.ActivityResponse

interface BoredNetworkDataSource {

    suspend fun getActivity(type: String? = null): ActivityResponse
}
