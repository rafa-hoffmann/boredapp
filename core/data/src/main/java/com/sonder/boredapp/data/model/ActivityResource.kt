package com.sonder.boredapp.data.model

import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import com.sonder.boredapp.network.di.model.ActivityResponse

fun ActivityResponse.asResource() = ActivityResource(
    activity = activity,
    accessibility = accessibility,
    type = ActivityType.values().first { it.apiName == type },
    participants = participants,
    price = price,
    key = key
)
