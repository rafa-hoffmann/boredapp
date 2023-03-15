package com.sonder.boredapp.data.repository

import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun getActivity(type: ActivityType? = null): Flow<ActivityResource>

    suspend fun clearPreviouslyFetchedActivities()

    suspend fun addUserActivity(activityResource: ActivityResource): Flow<Unit>

    suspend fun getUserActivities(type: ActivityType? = null): Flow<List<ActivityResource>>

    suspend fun updateActivityStatus(activityResource: ActivityResource): Flow<Unit>
}
