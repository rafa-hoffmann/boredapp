package com.sonder.boredapp.data.repository

import com.sonder.boredapp.common.network.BoredDispatchers
import com.sonder.boredapp.common.network.Dispatcher
import com.sonder.boredapp.data.model.asEntity
import com.sonder.boredapp.data.model.asResource
import com.sonder.boredapp.database.dao.ActivityDao
import com.sonder.boredapp.database.model.asExternalModel
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
    private val activityDao: ActivityDao
) : ActivityRepository {

    private val networkActivitySet: HashSet<String> = hashSetOf()
    override suspend fun getActivity(type: ActivityType?): Flow<ActivityResource> = flow {
        val activityResource = network.getActivity(type?.apiName).asResource()

        if (!networkActivitySet.contains(activityResource.key)) {
            networkActivitySet.add(activityResource.key)
            emit(network.getActivity(type?.apiName).asResource())
        }
    }.flowOn(ioDispatcher)

    override suspend fun addUserActivity(activityResource: ActivityResource) = flow {
        emit(activityDao.upsertActivity(activityResource.asEntity()))
    }.flowOn(ioDispatcher)

    override suspend fun getUserActivities(type: ActivityType?) = flow {
        emit(activityDao.getUserActivityEntities().asExternalModel())
    }.flowOn(ioDispatcher)
}
