package com.sonder.boredapp.data.repository

import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun getActivity(type: ActivityType? = null): Flow<List<ActivityResource>>
}
