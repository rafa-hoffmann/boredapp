package com.sonder.boredapp.data.repository

import com.sonder.boredapp.common.network.Dispatcher
import com.sonder.boredapp.common.network.BoredDispatchers
import com.sonder.boredapp.data.model.asResource
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import com.sonder.boredapp.network.di.retrofit.RetrofitBoredNetwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    @Dispatcher(BoredDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val network: RetrofitBoredNetwork,
) : ActivityRepository {
    override suspend fun getActivity(type: ActivityType?): Flow<ActivityResource> =
        flow {
            emit(network.getActivity(type?.apiName).asResource())
        }.flowOn(ioDispatcher)
}
