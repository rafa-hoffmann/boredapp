package com.sonder.boredapp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityStatus
import com.sonder.boredapp.model.data.ActivityType
import kotlinx.datetime.Instant

@Entity(
    tableName = "activities"
)
data class ActivityEntity(
    val activity: String,
    val accessibility: Float,
    val type: ActivityType,
    val participants: Int,
    val price: Float,
    @PrimaryKey val key: String,

    val status: Type?,

    val startTime: Instant?,
    val finishTime: Instant?
) {
    enum class Type {
        WITHDRAWAL,
        IN_PROGRESS,
        FINISHED
    }
}

fun ActivityEntity.asExternalModel() = ActivityResource(
    activity = activity,
    accessibility = accessibility,
    type = type,
    participants = participants,
    price = price,
    key = key,
    status = when (status) {
        ActivityEntity.Type.FINISHED -> ActivityStatus.Finished(
            startTime!!,
            finishTime!!
        )
        ActivityEntity.Type.IN_PROGRESS -> ActivityStatus.InProgress(
            startTime!!
        )
        ActivityEntity.Type.WITHDRAWAL -> ActivityStatus.Withdrawal
        else -> null
    }
)
