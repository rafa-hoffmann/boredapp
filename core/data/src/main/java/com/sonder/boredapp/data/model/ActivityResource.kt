package com.sonder.boredapp.data.model

import com.sonder.boredapp.database.model.ActivityEntity
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityStatus
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
fun ActivityResource.asEntity() = ActivityEntity(
    activity = activity,
    accessibility = accessibility,
    type = type,
    participants = participants,
    price = price,
    key = key,
    status = when (status) {
        is ActivityStatus.Finished -> ActivityEntity.Type.WITHDRAWAL
        is ActivityStatus.InProgress -> ActivityEntity.Type.IN_PROGRESS
        ActivityStatus.Withdrawal -> ActivityEntity.Type.WITHDRAWAL
        null -> null
    },
    startTime = when (status) {
        is ActivityStatus.Finished -> (status as ActivityStatus.Finished).startTime
        is ActivityStatus.InProgress -> (status as ActivityStatus.InProgress).startTime
        ActivityStatus.Withdrawal, null -> null
    },
    finishTime = when (status) {
        is ActivityStatus.Finished -> (status as ActivityStatus.Finished).finishTime
        is ActivityStatus.InProgress, ActivityStatus.Withdrawal, null -> null
    }
)
